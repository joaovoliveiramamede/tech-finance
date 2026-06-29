import { useRouter } from 'expo-router';
import { useMemo, useState } from 'react';
import { StyleSheet, Text } from 'react-native';

import { AuthLayout } from '@/components/ui/auth-layout';
import { Button } from '@/components/ui/button';
import { InputField } from '@/components/ui/input-field';
import { AppColors } from '@/constants/app-theme';
import { useAuth } from '@/contexts/auth-context';
import { accountApiService } from '@/core/services/account-api.service';
import { resolveErrorMessage } from '@/core/utils/formatters';

export default function CreateAccountScreen() {
  const { name } = useAuth();
  const router = useRouter();

  const [accountName, setAccountName] = useState('');
  const [balance, setBalance] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const welcomeSubtitle = useMemo(() => {
    const displayName = name?.trim() || 'Olá';
    return `${displayName}! Crie sua primeira conta para começar a usar o painel.`;
  }, [name]);

  async function submit() {
    setError(null);

    const trimmedName = accountName.trim();

    if (!trimmedName) {
      setError('Informe o nome da conta.');
      return;
    }

    const normalizedBalance = balance.trim().replace(',', '.');

    if (!normalizedBalance) {
      setError('Informe o saldo inicial.');
      return;
    }

    const parsedBalance = Number(normalizedBalance);

    if (Number.isNaN(parsedBalance)) {
      setError('Saldo inválido.');
      return;
    }

    setLoading(true);

    try {
      await accountApiService.createAccount(trimmedName, parsedBalance);
      router.replace('/(app)');
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao criar conta.'));
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthLayout title="Primeira conta" subtitle={welcomeSubtitle}>
      <InputField
        placeholder="Nome da conta"
        value={accountName}
        onChangeText={setAccountName}
      />
      <InputField
        placeholder="Saldo inicial (ex: 1500.00)"
        keyboardType="decimal-pad"
        value={balance}
        onChangeText={setBalance}
      />

      {error ? <Text style={styles.error}>{error}</Text> : null}

      <Button
        title={loading ? 'Criando...' : 'Criar conta e continuar'}
        loading={loading}
        onPress={submit}
      />
    </AuthLayout>
  );
}

const styles = StyleSheet.create({
  error: {
    color: AppColors.error,
    fontSize: 14,
  },
});
