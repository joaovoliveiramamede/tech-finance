import { useCallback, useState } from 'react';
import { RefreshControl, ScrollView, StyleSheet, Text, View } from 'react-native';

import { Card, PageHeader, StatCard } from '@/components/ui/card';
import { AppColors, Spacing } from '@/constants/app-theme';
import { AccountResponse } from '@/core/models/account.model';
import { CategoryResponse } from '@/core/models/category.model';
import { TransactionResponse } from '@/core/models/transaction.model';
import { accountApiService } from '@/core/services/account-api.service';
import { categoryApiService } from '@/core/services/category-api.service';
import { transactionApiService } from '@/core/services/transaction-api.service';
import { formatCurrency, formatDateTime } from '@/core/utils/formatters';
import { useFocusEffect } from 'expo-router';

export default function DashboardScreen() {
  const [accounts, setAccounts] = useState<AccountResponse[]>([]);
  const [transactions, setTransactions] = useState<TransactionResponse[]>([]);
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [refreshing, setRefreshing] = useState(false);

  const loadData = useCallback(async () => {
    try {
      const [accountsData, transactionsData, categoriesData] = await Promise.all([
        accountApiService.findAll(),
        transactionApiService.findAll(),
        categoryApiService.findAll(),
      ]);

      setAccounts(accountsData);
      setTransactions(transactionsData);
      setCategories(categoriesData);
      setError(null);
    } catch {
      setAccounts([]);
      setTransactions([]);
      setCategories([]);
      setError('Erro ao carregar dados.');
    }
  }, []);

  useFocusEffect(
    useCallback(() => {
      loadData();
    }, [loadData]),
  );

  async function onRefresh() {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
  }

  const totalBalance = accounts.reduce((sum, account) => sum + (account.saldo ?? 0), 0);
  const income = transactions
    .filter((t) => t.tipo === 'income')
    .reduce((sum, t) => sum + (t.valor ?? 0), 0);
  const expense = transactions
    .filter((t) => t.tipo === 'expense')
    .reduce((sum, t) => sum + (t.valor ?? 0), 0);

  const lastTransactions = [...transactions]
    .sort((left, right) => {
      const leftDate = left.ocorreu_em ? new Date(left.ocorreu_em).getTime() : 0;
      const rightDate = right.ocorreu_em ? new Date(right.ocorreu_em).getTime() : 0;
      return rightDate - leftDate;
    })
    .slice(0, 5);

  const topCategories = categories.slice(0, 5);

  return (
    <ScrollView
      style={styles.container}
      contentContainerStyle={styles.content}
      refreshControl={<RefreshControl refreshing={refreshing} onRefresh={onRefresh} />}>
      <PageHeader title="Home" subtitle="Resumo geral das suas finanças" />

      <View style={styles.stats}>
        <StatCard label="Saldo total" value={formatCurrency(totalBalance)} />
        <StatCard label="Receitas do mês" value={formatCurrency(income)} valueColor={AppColors.income} />
        <StatCard label="Despesas do mês" value={formatCurrency(expense)} valueColor={AppColors.expense} />
      </View>

      <Card>
        <Text style={styles.sectionTitle}>Últimas transações</Text>
        {error ? (
          <Text style={styles.muted}>{error}</Text>
        ) : lastTransactions.length === 0 ? (
          <Text style={styles.muted}>Nenhuma transação registrada</Text>
        ) : (
          <View style={styles.list}>
            {lastTransactions.map((transaction) => (
              <Text key={transaction.id} style={styles.listItem}>
                {formatDateTime(transaction.ocorreu_em)} • {transaction.descricao} •{' '}
                {formatCurrency(transaction.valor)}
              </Text>
            ))}
          </View>
        )}
      </Card>

      <Card>
        <Text style={styles.sectionTitle}>Categorias em destaque</Text>
        {topCategories.length === 0 ? (
          <Text style={styles.muted}>Nenhuma categoria registrada</Text>
        ) : (
          <View style={styles.list}>
            {topCategories.map((category) => (
              <Text key={category.id} style={styles.listItem}>
                {category.nome}
              </Text>
            ))}
          </View>
        )}
      </Card>
    </ScrollView>
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
  stats: {
    gap: Spacing.sm,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: AppColors.textPrimary,
    marginBottom: Spacing.sm,
  },
  list: {
    gap: Spacing.sm,
  },
  listItem: {
    fontSize: 14,
    color: AppColors.textSecondary,
    lineHeight: 20,
  },
  muted: {
    fontSize: 14,
    color: AppColors.textMuted,
  },
});
