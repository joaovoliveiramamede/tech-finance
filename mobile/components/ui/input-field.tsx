import React from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  TextInputProps,
  View,
} from 'react-native';

import { AppColors, Radius, Spacing } from '@/constants/app-theme';

interface InputFieldProps extends TextInputProps {
  label?: string;
}

export function InputField({ label, style, ...props }: InputFieldProps) {
  return (
    <View style={styles.container}>
      {label ? <Text style={styles.label}>{label}</Text> : null}
      <TextInput
        placeholderTextColor={AppColors.textMuted}
        style={[styles.input, style]}
        {...props}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    gap: Spacing.xs,
  },
  label: {
    fontSize: 14,
    fontWeight: '600',
    color: AppColors.textPrimary,
  },
  input: {
    borderWidth: 1,
    borderColor: AppColors.cardBorder,
    borderRadius: Radius.lg,
    paddingHorizontal: Spacing.md,
    paddingVertical: 14,
    fontSize: 16,
    color: AppColors.textPrimary,
    backgroundColor: AppColors.white,
  },
});
