import { useCallback, useState } from 'react';
import { RefreshControl, ScrollView, StyleSheet, Text } from 'react-native';
import { useFocusEffect } from 'expo-router';

import { AppModal, ModalFooter } from '@/components/ui/app-modal';
import { Button } from '@/components/ui/button';
import { PageHeader } from '@/components/ui/card';
import { DataList, TableColumn } from '@/components/ui/data-list';
import { InputField } from '@/components/ui/input-field';
import { SelectField } from '@/components/ui/select-field';
import { AppColors, Spacing } from '@/constants/app-theme';
import { AccountResponse } from '@/core/models/account.model';
import { CategoryResponse } from '@/core/models/category.model';
import {
  TransactionResponse,
  TransactionType,
} from '@/core/models/transaction.model';
import { accountApiService } from '@/core/services/account-api.service';
import { categoryApiService } from '@/core/services/category-api.service';
import { transactionApiService } from '@/core/services/transaction-api.service';
import {
  formatCurrency,
  formatDateTime,
  formatTransactionApiDate,
  formatTransactionType,
  resolveErrorMessage,
} from '@/core/utils/formatters';

const TRANSACTION_TYPES: { value: TransactionType; label: string }[] = [
  { value: 'income', label: 'Receita' },
  { value: 'expense', label: 'Despesa' },
];

export default function TransactionsScreen() {
  const [accounts, setAccounts] = useState<AccountResponse[]>([]);
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [transactions, setTransactions] = useState<TransactionResponse[]>([]);
  const [accountNames, setAccountNames] = useState<Map<string, string>>(new Map());
  const [categoryNames, setCategoryNames] = useState<Map<string, string>>(new Map());

  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [creating, setCreating] = useState(false);

  const [accountId, setAccountId] = useState('');
  const [categoryId, setCategoryId] = useState('');
  const [type, setType] = useState<TransactionType>('income');
  const [amount, setAmount] = useState('');
  const [description, setDescription] = useState('');

  const columns: TableColumn<TransactionResponse>[] = [
    { key: 'data', header: 'Data', value: (t) => formatDateTime(t.ocorreu_em) },
    { key: 'descricao', header: 'Descrição', value: (t) => t.descricao || '-' },
    { key: 'conta', header: 'Conta', value: (t) => resolveAccountName(t) },
    { key: 'categoria', header: 'Categoria', value: (t) => resolveCategoryName(t) },
    { key: 'tipo', header: 'Tipo', value: (t) => formatTransactionType(t.tipo) },
    { key: 'valor', header: 'Valor', value: (t) => formatCurrency(t.valor) },
  ];

  function resolveAccountName(transaction: TransactionResponse): string {
    if (transaction.nome_conta?.trim()) {
      return transaction.nome_conta;
    }

    return accountNames.get(transaction.id_conta) ?? '-';
  }

  function resolveCategoryName(transaction: TransactionResponse): string {
    if (transaction.nome_categoria?.trim()) {
      return transaction.nome_categoria;
    }

    return categoryNames.get(transaction.id_categoria) ?? '-';
  }

  const loadData = useCallback(async () => {
    try {
      const [accountsData, categoriesData, transactionsData] = await Promise.all([
        accountApiService.findAll(),
        categoryApiService.findAll(),
        transactionApiService.findAll(),
      ]);

      setAccounts(accountsData);
      setCategories(categoriesData);
      setTransactions(transactionsData);
      setAccountNames(new Map(accountsData.map((a) => [a.id, a.nome])));
      setCategoryNames(new Map(categoriesData.map((c) => [c.id, c.nome])));
      setError(null);
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar transação.'));
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useFocusEffect(
    useCallback(() => {
      setLoading(true);
      loadData();
    }, [loadData]),
  );

  function openModal() {
    if (accounts.length === 0) {
      setError('Cadastre uma conta antes de lançar transações.');
      return;
    }

    if (categories.length === 0) {
      setError('Cadastre uma categoria antes de lançar transações.');
      return;
    }

    setAccountId(accounts[0]?.id ?? '');
    setCategoryId(categories[0]?.id ?? '');
    setType('income');
    setAmount('');
    setDescription('');
    setModalOpen(true);
  }

  async function create() {
    if (!accountId) {
      setError('Selecione uma conta.');
      return;
    }

    if (!categoryId) {
      setError('Selecione uma categoria.');
      return;
    }

    const normalizedAmount = amount.trim().replace(',', '.');

    if (!normalizedAmount) {
      setError('Informe o valor.');
      return;
    }

    const parsedAmount = Number(normalizedAmount);

    if (Number.isNaN(parsedAmount)) {
      setError('Valor inválido.');
      return;
    }

    setCreating(true);

    try {
      await transactionApiService.create({
        id_conta: accountId,
        id_categoria: categoryId,
        valor: parsedAmount,
        tipo: type,
        descricao: description.trim(),
        ocorreu_em: formatTransactionApiDate(),
      });
      setModalOpen(false);
      setError(null);
      await loadData();
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar transação.'));
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
              loadData();
            }}
          />
        }>
        <PageHeader
          title="Transações"
          subtitle="Lançamentos de receitas e despesas"
          action={<Button title="Nova transação" onPress={openModal} style={styles.actionButton} />}
        />

        {error ? <Text style={styles.error}>{error}</Text> : null}
        {loading ? <Text style={styles.muted}>Carregando...</Text> : null}

        {!loading ? (
          <DataList columns={columns} data={transactions} keyExtractor={(item) => item.id} />
        ) : null}
      </ScrollView>

      <AppModal
        visible={modalOpen}
        title="Nova transação"
        onClose={() => setModalOpen(false)}
        footer={
          <ModalFooter
            onCancel={() => setModalOpen(false)}
            onSubmit={create}
            loading={creating}
          />
        }>
        <SelectField
          label="Conta"
          value={accountId}
          options={accounts.map((a) => ({ value: a.id, label: a.nome }))}
          onValueChange={setAccountId}
        />
        <SelectField
          label="Categoria"
          value={categoryId}
          options={categories.map((c) => ({ value: c.id, label: c.nome }))}
          onValueChange={setCategoryId}
        />
        <SelectField
          label="Tipo"
          value={type}
          options={TRANSACTION_TYPES}
          onValueChange={setType}
        />
        <InputField
          label="Valor"
          placeholder="Valor"
          keyboardType="decimal-pad"
          value={amount}
          onChangeText={setAmount}
        />
        <InputField
          label="Descrição"
          placeholder="Descrição"
          value={description}
          onChangeText={setDescription}
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
