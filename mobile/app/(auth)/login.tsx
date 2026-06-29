import { Link, useRouter } from 'expo-router';
import { useState } from 'react';
import { StyleSheet, Text } from 'react-native';

import { AuthLayout } from '@/components/ui/auth-layout';
import { Button } from '@/components/ui/button';
import { InputField } from '@/components/ui/input-field';
import { AppColors } from '@/constants/app-theme';
import { useAuth } from '@/contexts/auth-context';
import { resolveErrorMessage } from '@/core/utils/formatters';

export default function LoginScreen() {
  const { login } = useAuth();
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  async function submit() {
    setError(null);

    if (!username.trim()) {
      setError('Informe seu usuário.');
      return;
    }

    if (!password.trim()) {
      setError('Informe sua senha.');
      return;
    }

    setLoading(true);

    try {
      await login({ usuario: username.trim(), senha: password });
      router.replace('/(app)');
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao realizar login.'));
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthLayout title="Entrar" subtitle="Acesse seu painel financeiro">
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

      <Button title={loading ? 'Entrando...' : 'Entrar'} loading={loading} onPress={submit} />

      <Link href="/(auth)/register" asChild>
        <Button title="Criar conta" variant="link" />
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
