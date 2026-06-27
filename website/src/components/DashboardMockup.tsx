export function DashboardMockup() {
  return (
    <div className="rounded-2xl border border-white/10 bg-[#f5f7fb] p-3 shadow-2xl shadow-black/30">
      <div className="overflow-hidden rounded-xl border border-[#e4e8ef] bg-white">
        <div className="flex min-h-[320px]">
          <aside className="w-28 shrink-0 bg-[#172033] p-3 text-white lg:w-32">
            <p className="text-xs font-bold">TechFinance</p>
            <p className="mt-1 text-[10px] text-[#9da8ba]">Pessoal</p>
            <div className="mt-4 space-y-1.5">
              <div className="rounded-md bg-[#2f6fed] px-2 py-1.5 text-[10px] font-medium">
                Dashboard
              </div>
              <div className="px-2 py-1.5 text-[10px] text-[#c7d0df]">Contas</div>
              <div className="px-2 py-1.5 text-[10px] text-[#c7d0df]">
                Transações
              </div>
              <div className="px-2 py-1.5 text-[10px] text-[#c7d0df]">
                Categorias
              </div>
            </div>
          </aside>

          <div className="min-w-0 flex-1 bg-[#f5f7fb] p-3">
            <div className="mb-3 h-8 rounded-md border border-[#e4e8ef] bg-white" />
            <div className="grid grid-cols-3 gap-2">
              <MetricCard label="Saldo total" value="R$ 12.450,00" />
              <MetricCard
                label="Receitas"
                value="R$ 8.200,00"
                valueClass="text-[#0f8f5f]"
              />
              <MetricCard
                label="Despesas"
                value="R$ 3.150,00"
                valueClass="text-[#c2413d]"
              />
            </div>
            <div className="mt-2 grid grid-cols-2 gap-2">
              <Panel title="Últimas transações" lines={3} />
              <Panel title="Categorias" lines={3} />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

function MetricCard({
  label,
  value,
  valueClass = 'text-[#172033]',
}: {
  label: string
  value: string
  valueClass?: string
}) {
  return (
    <div className="rounded-lg border border-[#e4e8ef] bg-white p-2">
      <p className="text-[9px] text-[#697386]">{label}</p>
      <p className={`mt-1 text-[11px] font-bold ${valueClass}`}>{value}</p>
    </div>
  )
}

function Panel({ title, lines }: { title: string; lines: number }) {
  return (
    <div className="rounded-lg border border-[#e4e8ef] bg-white p-2">
      <p className="text-[10px] font-bold text-[#172033]">{title}</p>
      <div className="mt-2 space-y-1.5">
        {Array.from({ length: lines }).map((_, index) => (
          <div
            key={index}
            className="h-2 rounded bg-[#f5f7fb]"
            style={{ width: `${85 - index * 12}%` }}
          />
        ))}
      </div>
    </div>
  )
}
