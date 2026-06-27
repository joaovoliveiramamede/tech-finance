import { links } from '../config/links'
import { DashboardMockup } from './DashboardMockup'

export function Hero() {
  return (
    <section className="relative overflow-hidden bg-gradient-to-br from-[#0f172a] via-[#172033] to-[#1e3a8a] pt-28 pb-20 text-white lg:pt-32 lg:pb-28">
      <div className="pointer-events-none absolute -top-24 right-0 h-72 w-72 rounded-full bg-[#2f6fed]/20 blur-3xl" />
      <div className="pointer-events-none absolute bottom-0 left-0 h-64 w-64 rounded-full bg-blue-400/10 blur-3xl" />

      <div className="relative mx-auto grid max-w-6xl items-center gap-12 px-4 lg:grid-cols-2 lg:px-6">
        <div className="space-y-6">
          <span className="inline-flex rounded-full border border-white/15 bg-white/10 px-3 py-1 text-xs font-medium text-blue-100">
            Web + Desktop + API + Analytics
          </span>

          <h1 className="text-4xl font-bold leading-tight tracking-tight lg:text-5xl">
            Suas finanças sob controle, no desktop e na web.
          </h1>

          <p className="max-w-xl text-lg text-blue-100/90">
            O TechFinance Pessoal organiza contas, categorias e transações em um
            dashboard claro. Seguro, multi-usuário e pronto para crescer com
            análises e previsões financeiras.
          </p>

          <div className="flex flex-wrap gap-3">
            <a
              href={links.webApp}
              className="rounded-xl bg-[#2f6fed] px-6 py-3 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
            >
              Acessar versão web
            </a>
            <a
              href="#planos"
              className="rounded-xl border border-white/20 bg-white/10 px-6 py-3 text-sm font-bold text-white transition-colors hover:bg-white/15"
            >
              Ver planos
            </a>
          </div>

          <div className="flex flex-wrap gap-6 pt-2 text-sm text-blue-100/80">
            <span>✓ Cadastro em minutos</span>
            <span>✓ Dados isolados por usuário</span>
            <span>✓ Feito para o Brasil (R$)</span>
          </div>
        </div>

        <DashboardMockup />
      </div>
    </section>
  )
}
