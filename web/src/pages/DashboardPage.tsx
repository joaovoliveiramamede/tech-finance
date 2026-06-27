import { useEffect, useState, type ReactNode } from 'react'
import { accountApiService } from '../services/accountApiService'
import { categoryApiService } from '../services/categoryApiService'
import { transactionApiService } from '../services/transactionApiService'
import type { AccountResponse } from '../types/account'
import type { CategoryResponse } from '../types/category'
import type { TransactionResponse } from '../types/transaction'
import { formatCurrency, formatDateTime } from '../utils/formatters'

interface DashboardData {
  accounts: AccountResponse[]
  transactions: TransactionResponse[]
  categories: CategoryResponse[]
}

export function DashboardPage() {
  const [data, setData] = useState<DashboardData | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let active = true

    async function loadDashboard() {
      try {
        const [accounts, transactions, categories] = await Promise.all([
          accountApiService.findAll(),
          transactionApiService.findAll(),
          categoryApiService.findAll(),
        ])

        if (active) {
          setData({ accounts, transactions, categories })
          setError(null)
        }
      } catch {
        if (active) {
          setData({
            accounts: [],
            transactions: [],
            categories: [],
          })
          setError('Erro ao carregar dados.')
        }
      }
    }

    loadDashboard()

    return () => {
      active = false
    }
  }, [])

  const totalBalance =
    data?.accounts.reduce((sum, account) => sum + (account.saldo ?? 0), 0) ?? 0

  const income =
    data?.transactions
      .filter((transaction) => transaction.tipo === 'income')
      .reduce((sum, transaction) => sum + (transaction.valor ?? 0), 0) ?? 0

  const expense =
    data?.transactions
      .filter((transaction) => transaction.tipo === 'expense')
      .reduce((sum, transaction) => sum + (transaction.valor ?? 0), 0) ?? 0

  const lastTransactions = [...(data?.transactions ?? [])]
    .sort((left, right) => {
      const leftDate = left.ocorreu_em ? new Date(left.ocorreu_em).getTime() : 0
      const rightDate = right.ocorreu_em
        ? new Date(right.ocorreu_em).getTime()
        : 0

      return rightDate - leftDate
    })
    .slice(0, 5)

  const topCategories = (data?.categories ?? []).slice(0, 5)

  return (
    <div className="space-y-6">
      <div className="space-y-1">
        <h1 className="text-3xl font-bold text-[#172033]">Home</h1>
        <p className="text-sm text-[#697386]">Resumo geral das suas finanças</p>
      </div>

      <div className="grid gap-4 md:grid-cols-3">
        <MetricCard label="Saldo total" value={formatCurrency(totalBalance)} />
        <MetricCard
          label="Receitas do mês"
          value={formatCurrency(income)}
          valueClassName="text-[#0f8f5f]"
        />
        <MetricCard
          label="Despesas do mês"
          value={formatCurrency(expense)}
          valueClassName="text-[#c2413d]"
        />
      </div>

      <div className="grid gap-4 lg:grid-cols-2">
        <Panel title="Últimas transações">
          {error ? (
            <p className="text-sm text-[#8a94a6]">{error}</p>
          ) : lastTransactions.length === 0 ? (
            <p className="text-sm text-[#8a94a6]">Nenhuma transação registrada</p>
          ) : (
            <ul className="space-y-3">
              {lastTransactions.map((transaction) => (
                <li
                  key={transaction.id}
                  className="text-sm leading-relaxed text-[#697386]"
                >
                  {formatDateTime(transaction.ocorreu_em)} •{' '}
                  {transaction.descricao} • {formatCurrency(transaction.valor)}
                </li>
              ))}
            </ul>
          )}
        </Panel>

        <Panel title="Categorias em destaque">
          {topCategories.length === 0 ? (
            <p className="text-sm text-[#8a94a6]">Nenhuma categoria registrada</p>
          ) : (
            <ul className="space-y-2">
              {topCategories.map((category) => (
                <li key={category.id} className="text-sm text-[#697386]">
                  {category.nome}
                </li>
              ))}
            </ul>
          )}
        </Panel>
      </div>
    </div>
  )
}

function MetricCard({
  label,
  value,
  valueClassName = 'text-[#172033]',
}: {
  label: string
  value: string
  valueClassName?: string
}) {
  return (
    <div className="min-h-28 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
      <p className="text-sm text-[#697386]">{label}</p>
      <p className={`mt-2 text-2xl font-bold ${valueClassName}`}>{value}</p>
    </div>
  )
}

function Panel({
  title,
  children,
}: {
  title: string
  children: ReactNode
}) {
  return (
    <div className="min-h-56 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
      <h2 className="text-base font-bold text-[#172033]">{title}</h2>
      <div className="mt-4">{children}</div>
    </div>
  )
}
