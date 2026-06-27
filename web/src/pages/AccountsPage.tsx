import { type FormEvent, useCallback, useEffect, useState } from 'react'
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
import type { AccountResponse } from '../types/account'
import {
  formatCurrency,
  formatDateTime,
  resolveErrorMessage,
} from '../utils/formatters'

export function AccountsPage() {
  const [accounts, setAccounts] = useState<AccountResponse[]>([])
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)
  const [modalOpen, setModalOpen] = useState(false)
  const [creating, setCreating] = useState(false)
  const [name, setName] = useState('')
  const [balance, setBalance] = useState('')

  const loadAccounts = useCallback(async () => {
    setLoading(true)

    try {
      const data = await accountApiService.findAll()
      setAccounts(data)
      setError(null)
    } catch (loadError) {
      setError(resolveErrorMessage(loadError, 'Erro ao processar conta.'))
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    loadAccounts()
  }, [loadAccounts])

  function openModal() {
    setName('')
    setBalance('')
    setModalOpen(true)
  }

  async function handleCreate(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const trimmedName = name.trim()

    if (!trimmedName) {
      setError('Informe o nome da conta.')
      return
    }

    const normalizedBalance = balance.trim().replace(',', '.')

    if (!normalizedBalance) {
      setError('Informe o saldo inicial.')
      return
    }

    const parsedBalance = Number(normalizedBalance)

    if (Number.isNaN(parsedBalance)) {
      setError('Saldo inválido.')
      return
    }

    setCreating(true)

    try {
      await accountApiService.createAccount(trimmedName, parsedBalance)
      setModalOpen(false)
      setError(null)
      await loadAccounts()
    } catch (createError) {
      setError(resolveErrorMessage(createError, 'Erro ao processar conta.'))
    } finally {
      setCreating(false)
    }
  }

  return (
    <div className="space-y-5">
      <PageHeader
        title="Contas"
        subtitle="Carteiras, bancos e saldos"
        action={
          <PrimaryButton onClick={openModal}>Nova conta</PrimaryButton>
        }
      />

      {error && <p className="text-sm text-red-600">{error}</p>}

      {loading ? (
        <p className="text-sm text-[#8a94a6]">Carregando...</p>
      ) : (
        <DataTable
          data={accounts}
          columns={[
            {
              key: 'nome',
              header: 'Nome',
              render: (account) => account.nome,
            },
            {
              key: 'tipo',
              header: 'Tipo',
              render: (account) => account.tipo ?? '-',
            },
            {
              key: 'saldo',
              header: 'Saldo',
              render: (account) => formatCurrency(account.saldo),
            },
            {
              key: 'criada',
              header: 'Criada em',
              render: (account) => formatDateTime(account.data_criacao),
            },
            {
              key: 'atualizada',
              header: 'Atualizada em',
              render: (account) => formatDateTime(account.data_atualizacao),
            },
          ]}
        />
      )}

      <Modal
        open={modalOpen}
        title="Nova conta"
        onClose={() => setModalOpen(false)}
        footer={
          <>
            <SecondaryButton onClick={() => setModalOpen(false)}>
              Cancelar
            </SecondaryButton>
            <PrimaryButton type="submit" form="create-account-form" disabled={creating}>
              {creating ? 'Salvando...' : 'Salvar'}
            </PrimaryButton>
          </>
        }
      >
        <form id="create-account-form" className="space-y-4" onSubmit={handleCreate}>
          <FormField label="Nome">
            <input
              type="text"
              value={name}
              onChange={(event) => setName(event.target.value)}
              placeholder="Nome da conta"
              className={fieldClassName}
            />
          </FormField>

          <FormField label="Saldo">
            <input
              type="text"
              inputMode="decimal"
              value={balance}
              onChange={(event) => setBalance(event.target.value)}
              placeholder="Saldo inicial"
              className={fieldClassName}
            />
          </FormField>
        </form>
      </Modal>
    </div>
  )
}
