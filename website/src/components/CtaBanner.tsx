import { links } from '../config/links'

export function CtaBanner() {
  return (
    <section className="py-20">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="rounded-3xl bg-gradient-to-r from-[#172033] to-[#1e3a8a] px-8 py-12 text-center text-white lg:px-16">
          <h2 className="text-3xl font-bold lg:text-4xl">
            Pronto para organizar suas finanças?
          </h2>
          <p className="mx-auto mt-4 max-w-2xl text-blue-100/90">
            Crie sua conta em minutos e comece a controlar saldo, gastos e
            receitas com clareza — no navegador ou no desktop.
          </p>
          <div className="mt-8 flex flex-wrap justify-center gap-3">
            <a
              href={links.webApp}
              className="rounded-xl bg-[#2f6fed] px-6 py-3 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
            >
              Criar conta grátis
            </a>
            <a
              href={`mailto:${links.contactEmail}?subject=Demonstração%20TechFinance`}
              className="rounded-xl border border-white/20 px-6 py-3 text-sm font-bold text-white transition-colors hover:bg-white/10"
            >
              Agendar demonstração
            </a>
          </div>
        </div>
      </div>
    </section>
  )
}
