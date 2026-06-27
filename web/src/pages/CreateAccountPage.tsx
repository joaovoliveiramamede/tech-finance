import { type FormEvent, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthLayout } from '../components/layout/AuthLayout'
import { useAuth } from '../context/AuthContext'
import { accountApiService } from '../services/accountApiService'
import { resolveErrorMessage } from '../utils/formatters'

export function CreateAccountPage() {
  const navigate = useNavigate()
  const { name } = useAuth()
  const [accountName, setAccountName] = useState('')
  const [balance, setBalance] = useState('')
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const welcomeName = name?.trim() || 'Olá'

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setError(null)

    const trimmedName = accountName.trim()

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

    setLoading(true)

    try {
      await accountApiService.createAccount(trimmedName, parsedBalance)
      navigate('/')
    } catch (submitError) {
      setError(resolveErrorMessage(submitError, 'Erro ao criar conta.'))
    } finally {
      setLoading(false)
    }
  }

  return (
    <AuthLayout
      title="Primeira conta"
      subtitle={`${welcomeName}! Crie sua primeira conta para começar a usar o painel.`}
    >
      <form className="space-y-4" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nome da conta"
          value={accountName}
          onChange={(event) => setAccountName(event.target.value)}
          className="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="text"
          inputMode="decimal"
          placeholder="Saldo inicial (ex: 1500.00)"
          value={balance}
          onChange={(event) => setBalance(event.target.value)}
          className="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        {error && <p className="text-sm text-red-600">{error}</p>}

        <button
          type="submit"
          disabled={loading}
          className="w-full cursor-pointer rounded-xl bg-blue-600 py-3 font-bold text-white transition-colors hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {loading ? 'Criando...' : 'Criar conta e continuar'}
        </button>
      </form>
    </AuthLayout>
  )
}
