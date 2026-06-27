import { links } from '../config/links'

export function Footer() {
  return (
    <footer className="border-t border-[#e4e8ef] bg-[#fafbfc] py-12">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <div className="grid gap-8 md:grid-cols-4">
          <div className="md:col-span-2">
            <p className="text-lg font-bold text-[#172033]">TechFinance</p>
            <p className="mt-2 max-w-sm text-sm text-[#697386]">
              Controle financeiro pessoal com web, desktop, API e analytics —
              desenvolvido por João Vitor Oliveira Mamede.
            </p>
          </div>

          <div>
            <p className="text-sm font-bold text-[#172033]">Produto</p>
            <ul className="mt-3 space-y-2 text-sm text-[#697386]">
              <li>
                <a href="#recursos" className="hover:text-[#172033]">
                  Recursos
                </a>
              </li>
              <li>
                <a href="#plataformas" className="hover:text-[#172033]">
                  Plataformas
                </a>
              </li>
              <li>
                <a href="#planos" className="hover:text-[#172033]">
                  Planos
                </a>
              </li>
              <li>
                <a href={links.webApp} className="hover:text-[#172033]">
                  Acessar app
                </a>
              </li>
            </ul>
          </div>

          <div>
            <p className="text-sm font-bold text-[#172033]">Desenvolvedores</p>
            <ul className="mt-3 space-y-2 text-sm text-[#697386]">
              <li>
                <a
                  href={links.apiDocs}
                  target="_blank"
                  rel="noreferrer"
                  className="hover:text-[#172033]"
                >
                  Documentação API
                </a>
              </li>
              <li>
                <a
                  href={`mailto:${links.contactEmail}`}
                  className="hover:text-[#172033]"
                >
                  Contato comercial
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div className="mt-10 border-t border-[#e4e8ef] pt-6 text-center text-xs text-[#8a94a6]">
          © {new Date().getFullYear()} TechFinance Pessoal. Todos os direitos
          reservados.
        </div>
      </div>
    </footer>
  )
}
