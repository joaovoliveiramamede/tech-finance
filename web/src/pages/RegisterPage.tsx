import { type FormEvent, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { AuthLayout } from '../components/layout/AuthLayout'
import { useAuth } from '../context/AuthContext'
import { resolveErrorMessage } from '../utils/formatters'
import { normalize, validateRegister } from '../utils/validation'

export function RegisterPage() {
  const navigate = useNavigate()
  const { register } = useAuth()
  const [name, setName] = useState('')
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setError(null)

    const normalizedName = normalize(name)
    const normalizedUsername = normalize(username)
    const validationError = validateRegister(
      normalizedName,
      normalizedUsername,
      password,
    )

    if (validationError) {
      setError(validationError)
      return
    }

    setLoading(true)

    try {
      await register({
        nome: normalizedName,
        usuario: normalizedUsername,
        senha: password,
        papel: 'USER',
      })
      navigate('/criar-conta')
    } catch (submitError) {
      setError(resolveErrorMessage(submitError, 'Erro ao registrar usuário.'))
    } finally {
      setLoading(false)
    }
  }

  return (
    <AuthLayout title="Criar conta" subtitle="Comece a organizar suas finanças">
      <form className="space-y-4" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nome"
          value={name}
          onChange={(event) => setName(event.target.value)}
          className="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="text"
          placeholder="Usuário"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          className="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          className="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        {error && <p className="text-sm text-red-600">{error}</p>}

        <button
          type="submit"
          disabled={loading}
          className="w-full cursor-pointer rounded-xl bg-blue-600 py-3 font-bold text-white transition-colors hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {loading ? 'Registrando...' : 'Registrar'}
        </button>

        <Link
          to="/login"
          className="block w-full text-center text-blue-600 transition-colors hover:text-blue-700"
        >
          Já tenho conta
        </Link>
      </form>
    </AuthLayout>
  )
}
