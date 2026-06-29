import React from 'react';
import { FlatList, StyleSheet, Text, View } from 'react-native';

import { AppColors, Radius, Spacing } from '@/constants/app-theme';

export interface TableColumn<T> {
  key: string;
  header: string;
  value: (item: T) => string;
}

interface DataListProps<T> {
  columns: TableColumn<T>[];
  data: T[];
  keyExtractor: (item: T) => string;
}

export function DataList<T>({ columns, data, keyExtractor }: DataListProps<T>) {
  if (data.length === 0) {
    return (
      <View style={styles.empty}>
        <Text style={styles.emptyText}>Nenhum registro encontrado</Text>
      </View>
    );
  }

  return (
    <FlatList
      data={data}
      keyExtractor={keyExtractor}
      scrollEnabled={false}
      contentContainerStyle={styles.list}
      renderItem={({ item }) => (
        <View style={styles.row}>
          {columns.map((column) => (
            <View key={column.key} style={styles.cell}>
              <Text style={styles.cellLabel}>{column.header}</Text>
              <Text style={styles.cellValue}>{column.value(item)}</Text>
            </View>
          ))}
        </View>
      )}
    />
  );
}

const styles = StyleSheet.create({
  list: {
    gap: Spacing.sm,
  },
  row: {
    backgroundColor: AppColors.white,
    borderRadius: Radius.md,
    borderWidth: 1,
    borderColor: AppColors.cardBorder,
    padding: Spacing.md,
    gap: Spacing.sm,
  },
  cell: {
    gap: 2,
  },
  cellLabel: {
    fontSize: 12,
    fontWeight: '600',
    color: AppColors.textMuted,
    textTransform: 'uppercase',
  },
  cellValue: {
    fontSize: 15,
    color: AppColors.textPrimary,
  },
  empty: {
    padding: Spacing.lg,
    alignItems: 'center',
    backgroundColor: AppColors.white,
    borderRadius: Radius.md,
    borderWidth: 1,
    borderColor: AppColors.cardBorder,
  },
  emptyText: {
    color: AppColors.textMuted,
    fontSize: 14,
  },
});
