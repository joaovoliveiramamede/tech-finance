import { useEffect, useState } from 'react'
import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { accountApiService } from '../services/accountApiService'

export function ProtectedRoute() {
  const { isAuthenticated } = useAuth()

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  return <Outlet />
}

export function GuestRoute() {
  const { isAuthenticated } = useAuth()
  const location = useLocation()

  if (!isAuthenticated) {
    return <Outlet />
  }

  if (location.pathname === '/register') {
    return <Navigate to="/criar-conta" replace />
  }

  return <Navigate to="/" replace />
}

export function OnboardingGuard() {
  const [checking, setChecking] = useState(true)
  const [hasAccounts, setHasAccounts] = useState(false)

  useEffect(() => {
    let active = true

    async function checkAccounts() {
      try {
        const accounts = await accountApiService.findAll()

        if (active) {
          setHasAccounts(accounts.length > 0)
        }
      } catch {
        if (active) {
          setHasAccounts(false)
        }
      } finally {
        if (active) {
          setChecking(false)
        }
      }
    }

    checkAccounts()

    return () => {
      active = false
    }
  }, [])

  if (checking) {
    return (
      <div className="flex min-h-svh items-center justify-center bg-[#f5f7fb] text-sm text-[#697386]">
        Carregando...
      </div>
    )
  }

  if (!hasAccounts) {
    return <Navigate to="/criar-conta" replace />
  }

  return <Outlet />
}
