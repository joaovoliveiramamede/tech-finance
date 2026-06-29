import { Link, useRouter } from 'expo-router';
import { useState } from 'react';
import { StyleSheet, Text } from 'react-native';

import { AuthLayout } from '@/components/ui/auth-layout';
import { Button } from '@/components/ui/button';
import { InputField } from '@/components/ui/input-field';
import { AppColors } from '@/constants/app-theme';
import { useAuth } from '@/contexts/auth-context';
import { resolveErrorMessage } from '@/core/utils/formatters';
import { normalize, validateRegister } from '@/core/utils/validation';

export default function RegisterScreen() {
  const { register } = useAuth();
  const router = useRouter();

  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  async function submit() {
    setError(null);

    const normalizedName = normalize(name);
    const normalizedUsername = normalize(username);
    const validationError = validateRegister(normalizedName, normalizedUsername, password);

    if (validationError) {
      setError(validationError);
      return;
    }

    setLoading(true);

    try {
      await register({
        nome: normalizedName,
        usuario: normalizedUsername,
        senha: password,
        papel: 'USER',
      });
      router.replace('/(auth)/criar-conta');
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao registrar usuário.'));
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthLayout title="Criar conta" subtitle="Comece a organizar suas finanças">
      <InputField placeholder="Nome" value={name} onChangeText={setName} />
      <InputField
        placeholder="Usuário"
        autoCapitalize="none"
        value={username}
        onChangeText={setUsername}
      />
      <InputField
        placeholder="Senha"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
      />

      {error ? <Text style={styles.error}>{error}</Text> : null}

      <Button title={loading ? 'Registrando...' : 'Registrar'} loading={loading} onPress={submit} />

      <Link href="/(auth)/login" asChild>
        <Button title="Já tenho conta" variant="link" />
      </Link>
    </AuthLayout>
  );
}

const styles = StyleSheet.create({
  error: {
    color: AppColors.error,
    fontSize: 14,
  },
});
