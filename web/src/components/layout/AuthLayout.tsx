import type { ReactNode } from 'react'

interface AuthLayoutProps {
  title: string
  subtitle: string
  children: ReactNode
}

export function AuthLayout({ title, subtitle, children }: AuthLayoutProps) {
  return (
    <div className="flex min-h-svh items-center justify-center bg-gradient-to-br from-slate-900 to-blue-800 px-4 py-10">
      <div className="w-full max-w-md rounded-[22px] bg-white p-9 shadow-2xl">
        <div className="mb-6 space-y-1">
          <h1 className="text-3xl font-bold text-slate-900">{title}</h1>
          <p className="text-slate-500">{subtitle}</p>
        </div>
        {children}
      </div>
    </div>
  )
}
