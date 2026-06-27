export function ProductPreview() {
  return (
    <section className="py-20 lg:py-24">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="grid items-center gap-12 lg:grid-cols-2">
          <div>
            <h2 className="text-3xl font-bold text-[#172033] lg:text-4xl">
              Interface clara, foco no que importa
            </h2>
            <p className="mt-4 text-[#697386]">
              O painel foi desenhado para mostrar o essencial: saldo, fluxo do
              mês e movimentações recentes. Sidebar escura, cards limpos e
              métricas com cores que facilitam a leitura.
            </p>

            <ul className="mt-6 space-y-3 text-sm text-[#697386]">
              <li className="flex gap-2">
                <span className="text-[#0f8f5f]">●</span>
                Receitas em verde e despesas em vermelho
              </li>
              <li className="flex gap-2">
                <span className="text-[#2f6fed]">●</span>
                Tabelas para contas, transações e categorias
              </li>
              <li className="flex gap-2">
                <span className="text-[#2f6fed]">●</span>
                Onboarding guiado após o cadastro
              </li>
              <li className="flex gap-2">
                <span className="text-[#2f6fed]">●</span>
                Mesma identidade visual na web e no desktop
              </li>
            </ul>
          </div>

          <div className="rounded-2xl border border-[#e4e8ef] bg-[#f5f7fb] p-4">
            <div className="space-y-3">
              <PreviewRow
                title="Contas"
                subtitle="Carteiras, bancos e saldos"
                action="Nova conta"
              />
              <PreviewRow
                title="Transações"
                subtitle="Receitas e despesas"
                action="Nova transação"
              />
              <PreviewRow
                title="Categorias"
                subtitle="Classificação das movimentações"
                action="Nova categoria"
              />
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

function PreviewRow({
  title,
  subtitle,
  action,
}: {
  title: string
  subtitle: string
  action: string
}) {
  return (
    <div className="rounded-xl border border-[#e4e8ef] bg-white p-4">
      <div className="flex items-start justify-between gap-4">
        <div>
          <p className="font-bold text-[#172033]">{title}</p>
          <p className="text-sm text-[#697386]">{subtitle}</p>
        </div>
        <span className="shrink-0 rounded-lg bg-[#2f6fed] px-3 py-1.5 text-xs font-bold text-white">
          {action}
        </span>
      </div>
      <div className="mt-3 space-y-2">
        <div className="h-2 w-full rounded bg-[#f5f7fb]" />
        <div className="h-2 w-4/5 rounded bg-[#f5f7fb]" />
        <div className="h-2 w-3/5 rounded bg-[#f5f7fb]" />
      </div>
    </div>
  )
}
