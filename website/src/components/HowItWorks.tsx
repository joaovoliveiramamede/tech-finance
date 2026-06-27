const steps = [
  {
    step: '01',
    title: 'Crie sua conta',
    description:
      'Cadastre-se em segundos com nome, usuário e senha. Login seguro com JWT.',
  },
  {
    step: '02',
    title: 'Configure sua primeira conta',
    description:
      'Defina nome e saldo inicial da sua conta bancária ou carteira principal.',
  },
  {
    step: '03',
    title: 'Lance e acompanhe',
    description:
      'Registre transações, categorize gastos e acompanhe tudo no dashboard.',
  },
]

export function HowItWorks() {
  return (
    <section id="como-funciona" className="bg-[#f5f7fb] py-20 lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="mx-auto max-w-2xl text-center">
          <h2 className="text-3xl font-bold text-[#172033] lg:text-4xl">
            Como funciona
          </h2>
          <p className="mt-4 text-[#697386]">
            Em três passos você sai do cadastro para o controle completo das
            suas finanças.
          </p>
        </div>

        <div className="mt-12 grid gap-6 lg:grid-cols-3">
          {steps.map((item) => (
            <div
              key={item.step}
              className="rounded-2xl border border-[#e4e8ef] bg-white p-6"
            >
              <span className="text-sm font-bold text-[#2f6fed]">
                {item.step}
              </span>
              <h3 className="mt-3 text-xl font-bold text-[#172033]">
                {item.title}
              </h3>
              <p className="mt-2 text-sm leading-relaxed text-[#697386]">
                {item.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
