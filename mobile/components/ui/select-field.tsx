import React from 'react';
import { Picker } from '@react-native-picker/picker';
import { Platform, StyleSheet, Text, View } from 'react-native';

import { AppColors, Radius, Spacing } from '@/constants/app-theme';

export interface SelectOption<T extends string = string> {
  value: T;
  label: string;
}

interface SelectFieldProps<T extends string = string> {
  label: string;
  value: T;
  options: SelectOption<T>[];
  onValueChange: (value: T) => void;
}

export function SelectField<T extends string = string>({
  label,
  value,
  options,
  onValueChange,
}: SelectFieldProps<T>) {
  return (
    <View style={styles.container}>
      <Text style={styles.label}>{label}</Text>
      <View style={styles.pickerWrapper}>
        <Picker
          selectedValue={value}
          onValueChange={(itemValue) => onValueChange(itemValue as T)}
          style={styles.picker}
          dropdownIconColor={AppColors.textSecondary}>
          {options.map((option) => (
            <Picker.Item key={option.value} label={option.label} value={option.value} />
          ))}
        </Picker>
      </View>
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
  pickerWrapper: {
    borderWidth: 1,
    borderColor: AppColors.cardBorder,
    borderRadius: Radius.lg,
    overflow: 'hidden',
    backgroundColor: AppColors.white,
  },
  picker: {
    color: AppColors.textPrimary,
    ...(Platform.OS === 'ios' ? { height: 180 } : {}),
  },
});
