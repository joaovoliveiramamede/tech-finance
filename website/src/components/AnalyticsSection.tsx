const highlights = [
  'Totais de receitas e despesas por período',
  'Taxa de gastos e distribuição por categoria',
  'Previsão de saldo para os próximos meses',
  'Cenários Conservador, Base e Agressivo',
  'Recomendações automáticas de ação financeira',
]

export function AnalyticsSection() {
  return (
    <section className="py-20 lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="overflow-hidden rounded-3xl bg-gradient-to-br from-[#eef4ff] to-[#f5f7fb] border border-[#e4e8ef]">
          <div className="grid lg:grid-cols-2">
            <div className="p-8 lg:p-12">
              <span className="rounded-full bg-[#2f6fed]/10 px-3 py-1 text-xs font-bold text-[#2f6fed]">
                Diferencial
              </span>
              <h2 className="mt-4 text-3xl font-bold text-[#172033] lg:text-4xl">
                Analytics com previsões e cenários
              </h2>
              <p className="mt-4 text-[#697386]">
                Além do controle do dia a dia, o módulo Analytics transforma suas
                transações em insights — para você tomar decisões com dados, não
                com achismo.
              </p>

              <ul className="mt-6 space-y-3">
                {highlights.map((item) => (
                  <li
                    key={item}
                    className="flex gap-2 text-sm text-[#697386]"
                  >
                    <span className="text-[#2f6fed]">✓</span>
                    {item}
                  </li>
                ))}
              </ul>
            </div>

            <div className="flex items-center justify-center bg-[#172033] p-8 lg:p-12">
              <div className="w-full max-w-sm space-y-4">
                <ScenarioCard
                  label="Conservador"
                  value="82%"
                  description="Probabilidade de saldo positivo"
                />
                <ScenarioCard
                  label="Base"
                  value="R$ 4.280"
                  description="Previsão para próximo mês"
                  highlighted
                />
                <ScenarioCard
                  label="Agressivo"
                  value="MANTER"
                  description="Sugestão de ação"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

function ScenarioCard({
  label,
  value,
  description,
  highlighted = false,
}: {
  label: string
  value: string
  description: string
  highlighted?: boolean
}) {
  return (
    <div
      className={`rounded-xl border p-4 ${
        highlighted
          ? 'border-[#2f6fed] bg-[#22304a]'
          : 'border-white/10 bg-[#1c273d]'
      }`}
    >
      <p className="text-xs text-[#9da8ba]">{label}</p>
      <p className="mt-1 text-2xl font-bold text-white">{value}</p>
      <p className="mt-1 text-xs text-[#c7d0df]">{description}</p>
    </div>
  )
}
