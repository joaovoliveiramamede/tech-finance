import { TransactionType } from '../models/transaction.model';

const currencyFormatter = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

const dateTimeFormatter = new Intl.DateTimeFormat('pt-BR', {
  day: '2-digit',
  month: '2-digit',
  year: 'numeric',
  hour: '2-digit',
  minute: '2-digit',
  timeZone: 'America/Sao_Paulo',
});

export function formatCurrency(value: number | null | undefined): string {
  return currencyFormatter.format(value ?? 0);
}

export function formatDateTime(value: string | null | undefined): string {
  if (!value) {
    return '-';
  }

  return dateTimeFormatter.format(new Date(value));
}

export function formatTransactionType(
  type: TransactionType | null | undefined,
): string {
  if (type === 'income') {
    return 'Receita';
  }

  if (type === 'expense') {
    return 'Despesa';
  }

  return '-';
}

export function formatTransactionApiDate(date: Date = new Date()): string {
  const parts = new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
    timeZone: 'America/Sao_Paulo',
  }).formatToParts(date);

  const get = (type: Intl.DateTimeFormatPartTypes) =>
    parts.find((part) => part.type === type)?.value ?? '00';

  return `${get('day')}-${get('month')}-${get('year')} : ${get('hour')}:${get('minute')}`;
}

export function resolveErrorMessage(error: unknown, fallback: string): string {
  if (error instanceof Error && error.message) {
    return error.message;
  }

  return fallback;
}
