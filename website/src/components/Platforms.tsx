const platforms = [
  {
    name: 'Web',
    badge: 'Disponível',
    description:
      'Acesse pelo navegador, sem instalação. Login, dashboard, contas, transações e categorias.',
    stack: 'React · TypeScript · Vite · Tailwind',
    highlight: true,
  },
  {
    name: 'Desktop',
    badge: 'Disponível',
    description:
      'Aplicação JavaFX nativa para Windows, macOS e Linux com a mesma experiência da web.',
    stack: 'JavaFX · Guice · OkHttp',
    highlight: false,
  },
  {
    name: 'API REST',
    badge: 'Core',
    description:
      'Backend central com autenticação JWT, regras de negócio e documentação OpenAPI.',
    stack: 'Spring Boot · JPA · MySQL',
    highlight: false,
  },
  {
    name: 'Analytics',
    badge: 'Avançado',
    description:
      'Microserviço Python com previsões, métricas por período e cenários financeiros.',
    stack: 'FastAPI · Pydantic',
    highlight: false,
  },
]

export function Platforms() {
  return (
    <section id="plataformas" className="bg-[#172033] py-20 text-white lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="mx-auto max-w-2xl text-center">
          <h2 className="text-3xl font-bold lg:text-4xl">
            Um ecossistema, várias interfaces
          </h2>
          <p className="mt-4 text-[#9da8ba]">
            Todas as aplicações compartilham a mesma API e as mesmas regras de
            negócio — saldo, categorias e transações sempre consistentes.
          </p>
        </div>

        <div className="mt-12 grid gap-6 sm:grid-cols-2">
          {platforms.map((platform) => (
            <article
              key={platform.name}
              className={`rounded-2xl border p-6 ${
                platform.highlight
                  ? 'border-[#2f6fed] bg-[#22304a]'
                  : 'border-white/10 bg-[#1c273d]'
              }`}
            >
              <div className="flex items-center justify-between gap-3">
                <h3 className="text-xl font-bold">{platform.name}</h3>
                <span className="rounded-full bg-[#2f6fed]/20 px-3 py-1 text-xs font-medium text-blue-100">
                  {platform.badge}
                </span>
              </div>
              <p className="mt-3 text-sm leading-relaxed text-[#c7d0df]">
                {platform.description}
              </p>
              <p className="mt-4 text-xs font-medium text-[#9da8ba]">
                {platform.stack}
              </p>
            </article>
          ))}
        </div>

        <div className="mt-10 rounded-2xl border border-white/10 bg-[#1c273d] p-6 text-center">
          <p className="text-sm text-[#c7d0df]">
            Arquitetura modular com Maven multi-módulo, hexagonal no backend e
            clientes desacoplados — pronta para mobile, integrações e
            microserviços.
          </p>
        </div>
      </div>
    </section>
  )
}
