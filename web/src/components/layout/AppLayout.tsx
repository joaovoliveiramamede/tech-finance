import { NavLink, Outlet, useLocation } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

const navItems = [
  { to: '/', label: 'Dashboard', end: true },
  { to: '/contas', label: 'Contas', end: false },
  { to: '/transacoes', label: 'Transações', end: false },
  { to: '/categorias', label: 'Categorias', end: false },
]

const pageTitles: Record<string, string> = {
  '/': 'Dashboard',
  '/contas': 'Contas',
  '/transacoes': 'Transações',
  '/categorias': 'Categorias',
}

export function AppLayout() {
  const { name, username, logout } = useAuth()
  const location = useLocation()
  const displayName = name ?? username ?? 'Usuário'
  const pageTitle = pageTitles[location.pathname] ?? 'Dashboard'

  return (
    <div className="flex min-h-svh bg-[#f5f7fb]">
      <aside className="flex w-[248px] min-w-[248px] max-w-[248px] flex-col justify-between bg-[#172033] px-4 py-6">
        <div className="space-y-7">
          <div className="px-2">
            <h1 className="text-xl font-bold text-white">TechFinance</h1>
            <p className="text-xs text-[#9da8ba]">Controle financeiro pessoal</p>
          </div>

          <nav className="space-y-2">
            {navItems.map((item) => (
              <NavLink
                key={item.to}
                to={item.to}
                end={item.end}
                className={({ isActive }) =>
                  [
                    'block w-full rounded-lg px-3.5 py-3 text-left text-sm transition-colors',
                    isActive
                      ? 'bg-[#2f6fed] font-medium text-white'
                      : 'text-[#c7d0df] hover:bg-[#22304a] hover:text-white',
                  ].join(' ')
                }
              >
                {item.label}
              </NavLink>
            ))}
          </nav>
        </div>

        <div className="space-y-1 rounded-[10px] bg-[#202c43] p-3">
          <p className="font-bold text-white">{displayName}</p>
          <p className="text-xs text-[#9da8ba]">Usuário logado</p>
          <button
            type="button"
            onClick={logout}
            className="mt-1 cursor-pointer text-sm text-[#c7d0df] transition-colors hover:text-white"
          >
            Sair
          </button>
        </div>
      </aside>

      <div className="flex min-w-0 flex-1 flex-col">
        <header className="flex h-16 items-center border-b border-[#e4e8ef] bg-white px-6">
          <h2 className="text-lg font-bold text-[#172033]">{pageTitle}</h2>
        </header>

        <main className="flex-1 overflow-auto p-6">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
