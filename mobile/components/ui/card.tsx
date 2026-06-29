import React from 'react';
import { StyleSheet, Text, View, ViewProps } from 'react-native';

import { AppColors, Radius, Spacing } from '@/constants/app-theme';

interface CardProps extends ViewProps {
  children: React.ReactNode;
}

export function Card({ children, style, ...props }: CardProps) {
  return (
    <View style={[styles.card, style]} {...props}>
      {children}
    </View>
  );
}

interface StatCardProps {
  label: string;
  value: string;
  valueColor?: string;
}

export function StatCard({ label, value, valueColor }: StatCardProps) {
  return (
    <Card style={styles.statCard}>
      <Text style={styles.statLabel}>{label}</Text>
      <Text style={[styles.statValue, valueColor ? { color: valueColor } : null]}>{value}</Text>
    </Card>
  );
}

interface PageHeaderProps {
  title: string;
  subtitle: string;
  action?: React.ReactNode;
}

export function PageHeader({ title, subtitle, action }: PageHeaderProps) {
  return (
    <View style={styles.header}>
      <View style={styles.headerText}>
        <Text style={styles.title}>{title}</Text>
        <Text style={styles.subtitle}>{subtitle}</Text>
      </View>
      {action}
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: AppColors.white,
    borderRadius: Radius.md,
    borderWidth: 1,
    borderColor: AppColors.cardBorder,
    padding: Spacing.md + 4,
  },
  statCard: {
    minHeight: 100,
    gap: Spacing.sm,
  },
  statLabel: {
    fontSize: 14,
    color: AppColors.textSecondary,
  },
  statValue: {
    fontSize: 22,
    fontWeight: '700',
    color: AppColors.textPrimary,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    justifyContent: 'space-between',
    gap: Spacing.md,
  },
  headerText: {
    flex: 1,
    gap: Spacing.xs,
  },
  title: {
    fontSize: 28,
    fontWeight: '700',
    color: AppColors.textPrimary,
  },
  subtitle: {
    fontSize: 14,
    color: AppColors.textSecondary,
  },
});
