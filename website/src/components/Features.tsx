const features = [
  {
    title: 'Dashboard inteligente',
    description:
      'Visualize saldo total, receitas, despesas, últimas transações e categorias em destaque.',
    icon: '📊',
  },
  {
    title: 'Multi-conta',
    description:
      'Gerencie várias carteiras e contas bancárias com saldo atualizado automaticamente.',
    icon: '🏦',
  },
  {
    title: 'Transações',
    description:
      'Registre receitas e despesas com impacto imediato no saldo da conta vinculada.',
    icon: '💸',
  },
  {
    title: 'Categorias',
    description:
      'Classifique movimentações para entender para onde vai o seu dinheiro.',
    icon: '🏷️',
  },
  {
    title: 'Web e Desktop',
    description:
      'Mesma experiência no navegador ou no app JavaFX — um ecossistema, várias interfaces.',
    icon: '🖥️',
  },
  {
    title: 'Segurança',
    description:
      'Autenticação JWT, senhas com BCrypt e isolamento total dos dados por usuário.',
    icon: '🔒',
  },
]

export function Features() {
  return (
    <section id="recursos" className="py-20 lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="mx-auto max-w-2xl text-center">
          <h2 className="text-3xl font-bold text-[#172033] lg:text-4xl">
            Tudo que você precisa para organizar suas finanças
          </h2>
          <p className="mt-4 text-[#697386]">
            Funcionalidades pensadas para o dia a dia, com a mesma lógica de
            negócio em todas as plataformas.
          </p>
        </div>

        <div className="mt-12 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {features.map((feature) => (
            <article
              key={feature.title}
              className="rounded-2xl border border-[#e4e8ef] bg-white p-6 transition-shadow hover:shadow-lg"
            >
              <span className="text-2xl">{feature.icon}</span>
              <h3 className="mt-4 text-lg font-bold text-[#172033]">
                {feature.title}
              </h3>
              <p className="mt-2 text-sm leading-relaxed text-[#697386]">
                {feature.description}
              </p>
            </article>
          ))}
        </div>
      </div>
    </section>
  )
}
