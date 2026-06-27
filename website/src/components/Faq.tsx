const items = [
  {
    question: 'O TechFinance é gratuito?',
    answer:
      'A versão web pode ser usada gratuitamente para organizar suas finanças pessoais. Planos Profissional e Empresarial incluem desktop, suporte e customizações sob consulta.',
  },
  {
    question: 'Preciso instalar algo?',
    answer:
      'Para a versão web, basta o navegador. O app desktop JavaFX é opcional e indicado para quem prefere uma aplicação nativa instalada no computador.',
  },
  {
    question: 'Meus dados são privados?',
    answer:
      'Sim. Cada usuário acessa apenas seus próprios dados. A API utiliza autenticação JWT e isolamento por usuário em todas as operações.',
  },
  {
    question: 'Funciona no celular?',
    answer:
      'A versão web é responsiva e funciona no navegador mobile. Um app nativo para iOS e Android está no roadmap.',
  },
  {
    question: 'Posso integrar com outros sistemas?',
    answer:
      'Sim. A API REST documentada com OpenAPI permite integrações. O plano Empresarial inclui suporte para projetos customizados.',
  },
  {
    question: 'O que vem no roadmap?',
    answer:
      'Orçamentos, metas financeiras, transações recorrentes, mais tipos de conta, exportação CSV/PDF, Open Banking e aplicativo mobile.',
  },
]

export function Faq() {
  return (
    <section id="faq" className="py-20 lg:py-24">
      <div className="mx-auto max-w-3xl px-4 lg:px-6">
        <div className="text-center">
          <h2 className="text-3xl font-bold text-[#172033] lg:text-4xl">
            Perguntas frequentes
          </h2>
          <p className="mt-4 text-[#697386]">
            Tire suas dúvidas antes de começar.
          </p>
        </div>

        <div className="mt-10 space-y-4">
          {items.map((item) => (
            <details
              key={item.question}
              className="group rounded-xl border border-[#e4e8ef] bg-white p-5"
            >
              <summary className="cursor-pointer list-none font-semibold text-[#172033] marker:content-none">
                <span className="flex items-center justify-between gap-4">
                  {item.question}
                  <span className="text-[#2f6fed] transition-transform group-open:rotate-45">
                    +
                  </span>
                </span>
              </summary>
              <p className="mt-3 text-sm leading-relaxed text-[#697386]">
                {item.answer}
              </p>
            </details>
          ))}
        </div>
      </div>
    </section>
  )
}
