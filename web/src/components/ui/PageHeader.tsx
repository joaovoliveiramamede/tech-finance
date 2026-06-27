import type { ReactNode } from 'react'

interface PageHeaderProps {
  title: string
  subtitle: string
  action?: ReactNode
}

export function PageHeader({ title, subtitle, action }: PageHeaderProps) {
  return (
    <div className="flex flex-wrap items-start justify-between gap-4">
      <div className="space-y-1">
        <h1 className="text-3xl font-bold text-[#172033]">{title}</h1>
        <p className="text-sm text-[#697386]">{subtitle}</p>
      </div>
      {action}
    </div>
  )
}
