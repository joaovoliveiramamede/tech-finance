const STORAGE_KEY = 'techfinance_session'

export interface Session {
  token: string
  username: string
  name: string
  userId: string
}

function loadSession(): Session | null {
  const raw = localStorage.getItem(STORAGE_KEY)

  if (!raw) {
    return null
  }

  try {
    return JSON.parse(raw) as Session
  } catch {
    return null
  }
}

let session: Session | null = loadSession()

export function getSession(): Session | null {
  return session
}

export function setSession(value: Session): void {
  session = value
  localStorage.setItem(STORAGE_KEY, JSON.stringify(value))
}

export function clearSession(): void {
  session = null
  localStorage.removeItem(STORAGE_KEY)
}

export function isAuthenticated(): boolean {
  return Boolean(session?.token?.trim())
}

export function getToken(): string | null {
  return session?.token ?? null
}
