import { useCallback, useState } from 'react';
import { RefreshControl, ScrollView, StyleSheet, Text } from 'react-native';
import { useFocusEffect } from 'expo-router';

import { AppModal, ModalFooter } from '@/components/ui/app-modal';
import { Button } from '@/components/ui/button';
import { PageHeader } from '@/components/ui/card';
import { DataList, TableColumn } from '@/components/ui/data-list';
import { InputField } from '@/components/ui/input-field';
import { AppColors, Spacing } from '@/constants/app-theme';
import { CategoryResponse } from '@/core/models/category.model';
import { categoryApiService } from '@/core/services/category-api.service';
import { formatDateTime, resolveErrorMessage } from '@/core/utils/formatters';

export default function CategoriesScreen() {
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [creating, setCreating] = useState(false);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');

  const columns: TableColumn<CategoryResponse>[] = [
    { key: 'nome', header: 'Nome', value: (c) => c.nome },
    { key: 'descricao', header: 'Descrição', value: (c) => c.descricao || '-' },
    { key: 'criada', header: 'Criada em', value: (c) => formatDateTime(c.data_criacao) },
  ];

  const loadCategories = useCallback(async () => {
    try {
      const data = await categoryApiService.findAll();
      setCategories(data);
      setError(null);
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar categoria.'));
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useFocusEffect(
    useCallback(() => {
      setLoading(true);
      loadCategories();
    }, [loadCategories]),
  );

  function openModal() {
    setName('');
    setDescription('');
    setModalOpen(true);
  }

  async function create() {
    const trimmedName = name.trim();

    if (!trimmedName) {
      setError('Informe o nome da categoria.');
      return;
    }

    setCreating(true);

    try {
      await categoryApiService.create({ nome: trimmedName, descricao: description.trim() });
      setModalOpen(false);
      setError(null);
      await loadCategories();
    } catch (err) {
      setError(resolveErrorMessage(err, 'Erro ao processar categoria.'));
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
              loadCategories();
            }}
          />
        }>
        <PageHeader
          title="Categorias"
          subtitle="Classificação das suas movimentações"
          action={<Button title="Nova categoria" onPress={openModal} style={styles.actionButton} />}
        />

        {error ? <Text style={styles.error}>{error}</Text> : null}
        {loading ? <Text style={styles.muted}>Carregando...</Text> : null}

        {!loading ? (
          <DataList columns={columns} data={categories} keyExtractor={(item) => item.id} />
        ) : null}
      </ScrollView>

      <AppModal
        visible={modalOpen}
        title="Nova categoria"
        onClose={() => setModalOpen(false)}
        footer={
          <ModalFooter
            onCancel={() => setModalOpen(false)}
            onSubmit={create}
            loading={creating}
          />
        }>
        <InputField label="Nome" placeholder="Nome" value={name} onChangeText={setName} />
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
