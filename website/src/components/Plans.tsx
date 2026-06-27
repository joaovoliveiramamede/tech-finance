import { links } from '../config/links'

const plans = [
  {
    name: 'Pessoal',
    price: 'Grátis',
    period: 'para começar',
    description: 'Ideal para organizar suas finanças pessoais e testar o produto.',
    features: [
      'Dashboard completo',
      'Contas, transações e categorias',
      'Versão web',
      'Cadastro multi-usuário',
      'Suporte por e-mail',
    ],
    cta: 'Começar grátis',
    href: links.webApp,
    highlighted: false,
  },
  {
    name: 'Profissional',
    price: 'Sob consulta',
    period: 'licença anual',
    description:
      'Para profissionais e pequenos escritórios que precisam de desktop + suporte.',
    features: [
      'Tudo do plano Pessoal',
      'App Desktop JavaFX',
      'Implantação assistida',
      'Prioridade no suporte',
      'Personalização de marca',
    ],
    cta: 'Solicitar demonstração',
    href: `mailto:${links.contactEmail}?subject=TechFinance%20Profissional`,
    highlighted: true,
  },
  {
    name: 'Empresarial',
    price: 'Sob consulta',
    period: 'projeto customizado',
    description:
      'Integrações, analytics avançado, API dedicada e evolução sob medida.',
    features: [
      'Tudo do plano Profissional',
      'Módulo Analytics',
      'Integrações via API',
      'Ambiente dedicado',
      'Roadmap compartilhado',
    ],
    cta: 'Falar com vendas',
    href: `mailto:${links.contactEmail}?subject=TechFinance%20Empresarial`,
    highlighted: false,
  },
]

export function Plans() {
  return (
    <section id="planos" className="bg-[#f5f7fb] py-20 lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="mx-auto max-w-2xl text-center">
          <h2 className="text-3xl font-bold text-[#172033] lg:text-4xl">
            Planos para cada momento
          </h2>
          <p className="mt-4 text-[#697386]">
            Comece gratuitamente na web ou converse conosco sobre licenciamento
            desktop e soluções sob medida.
          </p>
        </div>

        <div className="mt-12 grid gap-6 lg:grid-cols-3">
          {plans.map((plan) => (
            <article
              key={plan.name}
              className={`flex flex-col rounded-2xl border bg-white p-6 ${
                plan.highlighted
                  ? 'border-[#2f6fed] shadow-xl shadow-[#2f6fed]/10'
                  : 'border-[#e4e8ef]'
              }`}
            >
              {plan.highlighted && (
                <span className="mb-4 w-fit rounded-full bg-[#2f6fed] px-3 py-1 text-xs font-bold text-white">
                  Mais popular
                </span>
              )}

              <h3 className="text-xl font-bold text-[#172033]">{plan.name}</h3>
              <p className="mt-2 text-3xl font-bold text-[#172033]">
                {plan.price}
              </p>
              <p className="text-sm text-[#697386]">{plan.period}</p>
              <p className="mt-4 text-sm text-[#697386]">{plan.description}</p>

              <ul className="mt-6 flex-1 space-y-2">
                {plan.features.map((feature) => (
                  <li
                    key={feature}
                    className="flex gap-2 text-sm text-[#697386]"
                  >
                    <span className="text-[#2f6fed]">✓</span>
                    {feature}
                  </li>
                ))}
              </ul>

              <a
                href={plan.href}
                className={`mt-8 block rounded-xl px-4 py-3 text-center text-sm font-bold transition-colors ${
                  plan.highlighted
                    ? 'bg-[#2f6fed] text-white hover:bg-[#235fcf]'
                    : 'border border-[#e4e8ef] text-[#172033] hover:bg-[#f5f7fb]'
                }`}
              >
                {plan.cta}
              </a>
            </article>
          ))}
        </div>
      </div>
    </section>
  )
}
