import React from 'react';
import {
  Modal,
  Pressable,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { AppColors, Radius, Spacing } from '@/constants/app-theme';
import { Button } from './button';

interface AppModalProps {
  visible: boolean;
  title: string;
  onClose: () => void;
  children: React.ReactNode;
  footer?: React.ReactNode;
}

export function AppModal({ visible, title, onClose, children, footer }: AppModalProps) {
  return (
    <Modal visible={visible} animationType="slide" transparent onRequestClose={onClose}>
      <View style={styles.overlay}>
        <SafeAreaView style={styles.sheet}>
          <View style={styles.header}>
            <Text style={styles.title}>{title}</Text>
            <Pressable onPress={onClose} hitSlop={12}>
              <Text style={styles.close}>✕</Text>
            </Pressable>
          </View>

          <ScrollView
            style={styles.body}
            contentContainerStyle={styles.bodyContent}
            keyboardShouldPersistTaps="handled">
            {children}
          </ScrollView>

          {footer ? <View style={styles.footer}>{footer}</View> : null}
        </SafeAreaView>
      </View>
    </Modal>
  );
}

interface ModalFooterProps {
  onCancel: () => void;
  onSubmit: () => void;
  submitLabel?: string;
  loading?: boolean;
}

export function ModalFooter({
  onCancel,
  onSubmit,
  submitLabel = 'Salvar',
  loading = false,
}: ModalFooterProps) {
  return (
    <View style={styles.footerRow}>
      <Button title="Cancelar" variant="secondary" onPress={onCancel} style={styles.footerButton} />
      <Button
        title={loading ? 'Salvando...' : submitLabel}
        onPress={onSubmit}
        loading={loading}
        style={styles.footerButton}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    justifyContent: 'flex-end',
    backgroundColor: 'rgba(0,0,0,0.4)',
  },
  sheet: {
    maxHeight: '90%',
    backgroundColor: AppColors.white,
    borderTopLeftRadius: Radius.xl,
    borderTopRightRadius: Radius.xl,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: Spacing.lg,
    paddingTop: Spacing.lg,
    paddingBottom: Spacing.md,
    borderBottomWidth: 1,
    borderBottomColor: AppColors.cardBorder,
  },
  title: {
    fontSize: 18,
    fontWeight: '700',
    color: AppColors.textPrimary,
  },
  close: {
    fontSize: 18,
    color: AppColors.textSecondary,
  },
  body: {
    maxHeight: 420,
  },
  bodyContent: {
    padding: Spacing.lg,
    gap: Spacing.md,
  },
  footer: {
    padding: Spacing.lg,
    borderTopWidth: 1,
    borderTopColor: AppColors.cardBorder,
  },
  footerRow: {
    flexDirection: 'row',
    gap: Spacing.sm,
  },
  footerButton: {
    flex: 1,
  },
});
