import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider } from '../context/AuthContext'
import { AppLayout } from '../components/layout/AppLayout'
import { GuestRoute, OnboardingGuard, ProtectedRoute } from './guards'
import { LoginPage } from '../pages/LoginPage'
import { RegisterPage } from '../pages/RegisterPage'
import { CreateAccountPage } from '../pages/CreateAccountPage'
import { DashboardPage } from '../pages/DashboardPage'
import { AccountsPage } from '../pages/AccountsPage'
import { TransactionsPage } from '../pages/TransactionsPage'
import { CategoriesPage } from '../pages/CategoriesPage'

export function AppRoutes() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route element={<GuestRoute />}>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
          </Route>

          <Route element={<ProtectedRoute />}>
            <Route path="/criar-conta" element={<CreateAccountPage />} />
            <Route element={<OnboardingGuard />}>
              <Route element={<AppLayout />}>
                <Route index element={<DashboardPage />} />
                <Route path="contas" element={<AccountsPage />} />
                <Route path="transacoes" element={<TransactionsPage />} />
                <Route path="categorias" element={<CategoriesPage />} />
              </Route>
            </Route>
          </Route>

          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}
