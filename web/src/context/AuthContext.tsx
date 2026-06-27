import {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
  type ReactNode,
} from 'react'
import { authService } from '../services/authService'
import {
  getSession,
  isAuthenticated as checkAuthenticated,
} from '../infrastructure/session/sessionManager'
import type { LoginRequest, RegisterRequest } from '../types/auth'

interface AuthContextValue {
  isAuthenticated: boolean
  username: string | null
  name: string | null
  login: (request: LoginRequest) => Promise<void>
  register: (request: RegisterRequest) => Promise<void>
  logout: () => void
}

const AuthContext = createContext<AuthContextValue | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [authenticated, setAuthenticated] = useState(checkAuthenticated())
  const [username, setUsername] = useState(getSession()?.username ?? null)
  const [name, setName] = useState(getSession()?.name ?? null)

  const syncSession = useCallback(() => {
    const session = getSession()
    setAuthenticated(checkAuthenticated())
    setUsername(session?.username ?? null)
    setName(session?.name ?? null)
  }, [])

  const login = useCallback(
    async (request: LoginRequest) => {
      await authService.login(request)
      syncSession()
    },
    [syncSession],
  )

  const register = useCallback(
    async (request: RegisterRequest) => {
      await authService.register(request)
      syncSession()
    },
    [syncSession],
  )

  const logout = useCallback(() => {
    authService.logout()
    syncSession()
  }, [syncSession])

  const value = useMemo(
    () => ({
      isAuthenticated: authenticated,
      username,
      name,
      login,
      register,
      logout,
    }),
    [authenticated, username, name, login, register, logout],
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth(): AuthContextValue {
  const context = useContext(AuthContext)

  if (!context) {
    throw new Error('useAuth deve ser usado dentro de AuthProvider')
  }

  return context
}
