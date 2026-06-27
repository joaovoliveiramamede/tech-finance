import { type FormEvent, useCallback, useEffect, useMemo, useState } from 'react'
import { DataTable } from '../components/ui/DataTable'
import {
  FormField,
  Modal,
  PrimaryButton,
  SecondaryButton,
  fieldClassName,
} from '../components/ui/Modal'
import { PageHeader } from '../components/ui/PageHeader'
import { accountApiService } from '../services/accountApiService'
import { categoryApiService } from '../services/categoryApiService'
import { transactionApiService } from '../services/transactionApiService'
import type { AccountResponse } from '../types/account'
import type { CategoryResponse } from '../types/category'
import type { TransactionResponse, TransactionType } from '../types/transaction'
import {
  formatCurrency,
  formatDateTime,
  formatTransactionApiDate,
  formatTransactionType,
  resolveErrorMessage,
} from '../utils/formatters'

const transactionTypes: { value: TransactionType; label: string }[] = [
  { value: 'income', label: 'Receita' },
  { value: 'expense', label: 'Despesa' },
]

export function TransactionsPage() {
  const [accounts, setAccounts] = useState<AccountResponse[]>([])
  const [categories, setCategories] = useState<CategoryResponse[]>([])
  const [transactions, setTransactions] = useState<TransactionResponse[]>([])
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)
  const [modalOpen, setModalOpen] = useState(false)
  const [creating, setCreating] = useState(false)

  const [accountId, setAccountId] = useState('')
  const [categoryId, setCategoryId] = useState('')
  const [type, setType] = useState<TransactionType>('income')
  const [amount, setAmount] = useState('')
  const [description, setDescription] = useState('')

  const accountNames = useMemo(
    () => new Map(accounts.map((account) => [account.id, account.nome])),
    [accounts],
  )

  const categoryNames = useMemo(
    () => new Map(categories.map((category) => [category.id, category.nome])),
    [categories],
  )

  const loadData = useCallback(async () => {
    setLoading(true)

    try {
      const [accountsData, categoriesData, transactionsData] = await Promise.all([
        accountApiService.findAll(),
        categoryApiService.findAll(),
        transactionApiService.findAll(),
      ])

      setAccounts(accountsData)
      setCategories(categoriesData)
      setTransactions(transactionsData)
      setError(null)
    } catch (loadError) {
      setError(resolveErrorMessage(loadError, 'Erro ao processar transação.'))
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    loadData()
  }, [loadData])

  function resolveAccountName(transaction: TransactionResponse): string {
    if (transaction.nome_conta?.trim()) {
      return transaction.nome_conta
    }

    return accountNames.get(transaction.id_conta) ?? '-'
  }

  function resolveCategoryName(transaction: TransactionResponse): string {
    if (transaction.nome_categoria?.trim()) {
      return transaction.nome_categoria
    }

    return categoryNames.get(transaction.id_categoria) ?? '-'
  }

  function openModal() {
    if (accounts.length === 0) {
      setError('Cadastre uma conta antes de lançar transações.')
      return
    }

    if (categories.length === 0) {
      setError('Cadastre uma categoria antes de lançar transações.')
      return
    }

    setAccountId(accounts[0]?.id ?? '')
    setCategoryId(categories[0]?.id ?? '')
    setType('income')
    setAmount('')
    setDescription('')
    setModalOpen(true)
  }

  async function handleCreate(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    if (!accountId) {
      setError('Selecione uma conta.')
      return
    }

    if (!categoryId) {
      setError('Selecione uma categoria.')
      return
    }

    const normalizedAmount = amount.trim().replace(',', '.')

    if (!normalizedAmount) {
      setError('Informe o valor.')
      return
    }

    const parsedAmount = Number(normalizedAmount)

    if (Number.isNaN(parsedAmount)) {
      setError('Valor inválido.')
      return
    }

    setCreating(true)

    try {
      await transactionApiService.create({
        id_conta: accountId,
        id_categoria: categoryId,
        valor: parsedAmount,
        tipo: type,
        descricao: description.trim(),
        ocorreu_em: formatTransactionApiDate(),
      })

      setModalOpen(false)
      setError(null)
      await loadData()
    } catch (createError) {
      setError(resolveErrorMessage(createError, 'Erro ao processar transação.'))
    } finally {
      setCreating(false)
    }
  }

  return (
    <div className="space-y-5">
      <PageHeader
        title="Transações"
        subtitle="Lançamentos de receitas e despesas"
        action={
          <PrimaryButton onClick={openModal}>Nova transação</PrimaryButton>
        }
      />

      {error && <p className="text-sm text-red-600">{error}</p>}

      {loading ? (
        <p className="text-sm text-[#8a94a6]">Carregando...</p>
      ) : (
        <DataTable
          data={transactions}
          columns={[
            {
              key: 'data',
              header: 'Data',
              render: (transaction) => formatDateTime(transaction.ocorreu_em),
            },
            {
              key: 'descricao',
              header: 'Descrição',
              render: (transaction) => transaction.descricao || '-',
            },
            {
              key: 'conta',
              header: 'Conta',
              render: (transaction) => resolveAccountName(transaction),
            },
            {
              key: 'categoria',
              header: 'Categoria',
              render: (transaction) => resolveCategoryName(transaction),
            },
            {
              key: 'tipo',
              header: 'Tipo',
              render: (transaction) => formatTransactionType(transaction.tipo),
            },
            {
              key: 'valor',
              header: 'Valor',
              render: (transaction) => formatCurrency(transaction.valor),
            },
          ]}
        />
      )}

      <Modal
        open={modalOpen}
        title="Nova transação"
        onClose={() => setModalOpen(false)}
        footer={
          <>
            <SecondaryButton onClick={() => setModalOpen(false)}>
              Cancelar
            </SecondaryButton>
            <PrimaryButton
              type="submit"
              form="create-transaction-form"
              disabled={creating}
            >
              {creating ? 'Salvando...' : 'Salvar'}
            </PrimaryButton>
          </>
        }
      >
        <form
          id="create-transaction-form"
          className="space-y-4"
          onSubmit={handleCreate}
        >
          <FormField label="Conta">
            <select
              value={accountId}
              onChange={(event) => setAccountId(event.target.value)}
              className={fieldClassName}
            >
              {accounts.map((account) => (
                <option key={account.id} value={account.id}>
                  {account.nome}
                </option>
              ))}
            </select>
          </FormField>

          <FormField label="Categoria">
            <select
              value={categoryId}
              onChange={(event) => setCategoryId(event.target.value)}
              className={fieldClassName}
            >
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.nome}
                </option>
              ))}
            </select>
          </FormField>

          <FormField label="Tipo">
            <select
              value={type}
              onChange={(event) => setType(event.target.value as TransactionType)}
              className={fieldClassName}
            >
              {transactionTypes.map((item) => (
                <option key={item.value} value={item.value}>
                  {item.label}
                </option>
              ))}
            </select>
          </FormField>

          <FormField label="Valor">
            <input
              type="text"
              inputMode="decimal"
              value={amount}
              onChange={(event) => setAmount(event.target.value)}
              placeholder="Valor"
              className={fieldClassName}
            />
          </FormField>

          <FormField label="Descrição">
            <input
              type="text"
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              placeholder="Descrição"
              className={fieldClassName}
            />
          </FormField>
        </form>
      </Modal>
    </div>
  )
}
