import { useCallback, useState } from 'react';
import { RefreshControl, ScrollView, StyleSheet, Text } from 'react-native';
import { useFocusEffect } from 'expo-router';

import { AppModal, ModalFooter } from '@/components/ui/app-modal';
import { Button } from '@/components/ui/button';
import { PageHeader } from '@/components/ui/card';
import { DataList, TableColumn } from '@/components/ui/data-list';
import { InputField } from '@/components/ui/input-field';
import { AppColors, Spacing } from '@/constants/app-theme';
import { AccountResponse } from '@/core/models/account.model';
import { accountApiService } from '@/core/services/account-api.service';
import {
  formatCurrency,
  formatDateTime,
  resolveErrorMessage,
} from '@/core/utils/formatters';

export default function AccountsScreen() {
  const [accounts, setAccounts] = useState<AccountResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [creating, setCreating] = useState(false);
  const [name, setName] = useState('');
  const [balance, setBalance] = useState('');

  const columns: TableColumn<AccountResponse>[] = [
    { key: 'nome', header: 'Nome', value: (a) => a.nome },
    { key: 'tipo', header: 'Tipo', value: (a) => a.tipo ?? '-' },
    { key: 'saldo', header: 'Saldo', value: (a) => formatCurrency(a.saldo) },
    { key: 'criada', header: 'Criada em', value: (a) => formatDateTime(a.data_criacao) },
    {
      key: 'atualizada',
      header: 'Atualizada em',
      value: (a) => formatDateTime(a.data_atualizacao),
    },
  ];

  const loadAccounts = useCallback(async () => {
    try {
      const data = await accountApiService.findAll();
      setAccounts(data);
      setError(null);
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar conta.'));
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useFocusEffect(
    useCallback(() => {
      setLoading(true);
      loadAccounts();
    }, [loadAccounts]),
  );

  function openModal() {
    setName('');
    setBalance('');
    setModalOpen(true);
  }

  async function create() {
    const trimmedName = name.trim();

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

    setCreating(true);

    try {
      await accountApiService.createAccount(trimmedName, parsedBalance);
      setModalOpen(false);
      setError(null);
      await loadAccounts();
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar conta.'));
    } finally {
      setCreating(false);
    }
  }

  return (
    <>
      <ScrollView
        style={styles.container}
        contentContainerStyle={styles.content}
        refreshControl={
          <RefreshControl
            refreshing={refreshing}
            onRefresh={() => {
              setRefreshing(true);
              loadAccounts();
            }}
          />
        }>
        <PageHeader
          title="Contas"
          subtitle="Carteiras, bancos e saldos"
          action={<Button title="Nova conta" onPress={openModal} style={styles.actionButton} />}
        />

        {error ? <Text style={styles.error}>{error}</Text> : null}
        {loading ? <Text style={styles.muted}>Carregando...</Text> : null}

        {!loading ? (
          <DataList columns={columns} data={accounts} keyExtractor={(item) => item.id} />
        ) : null}
      </ScrollView>

      <AppModal
        visible={modalOpen}
        title="Nova conta"
        onClose={() => setModalOpen(false)}
        footer={
          <ModalFooter
            onCancel={() => setModalOpen(false)}
            onSubmit={create}
            loading={creating}
          />
        }>
        <InputField label="Nome" placeholder="Nome da conta" value={name} onChangeText={setName} />
        <InputField
          label="Saldo"
          placeholder="Saldo inicial"
          keyboardType="decimal-pad"
          value={balance}
          onChangeText={setBalance}
        />
      </AppModal>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: AppColors.background,
  },
  content: {
    padding: Spacing.lg,
    gap: Spacing.md,
    paddingBottom: Spacing.xl,
  },
  actionButton: {
    paddingHorizontal: Spacing.md,
    paddingVertical: 10,
  },
  error: {
    color: AppColors.error,
    fontSize: 14,
  },
  muted: {
    color: AppColors.textMuted,
    fontSize: 14,
  },
});
