import { useEffect, useState } from 'react'
import { links } from '../config/links'

const navLinks = [
  { href: '#recursos', label: 'Recursos' },
  { href: '#como-funciona', label: 'Como funciona' },
  { href: '#plataformas', label: 'Plataformas' },
  { href: '#planos', label: 'Planos' },
  { href: '#faq', label: 'FAQ' },
]

export function Navbar() {
  const [scrolled, setScrolled] = useState(false)
  const [menuOpen, setMenuOpen] = useState(false)

  useEffect(() => {
    function onScroll() {
      setScrolled(window.scrollY > 12)
    }

    window.addEventListener('scroll', onScroll)
    return () => window.removeEventListener('scroll', onScroll)
  }, [])

  return (
    <header
      className={`fixed inset-x-0 top-0 z-50 transition-all ${
        scrolled
          ? 'border-b border-[#e4e8ef] bg-white/95 shadow-sm backdrop-blur'
          : 'bg-transparent'
      }`}
    >
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-4 lg:px-6">
        <a href="#" className="flex items-center gap-2.5">
          <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-[#172033] text-sm font-bold text-white">
            TF
          </span>
          <div>
            <p className="text-base font-bold text-[#172033]">TechFinance</p>
            <p className="text-xs text-[#697386]">Controle financeiro pessoal</p>
          </div>
        </a>

        <nav className="hidden items-center gap-8 md:flex">
          {navLinks.map((link) => (
            <a
              key={link.href}
              href={link.href}
              className="text-sm font-medium text-[#697386] transition-colors hover:text-[#172033]"
            >
              {link.label}
            </a>
          ))}
        </nav>

        <div className="hidden items-center gap-3 md:flex">
          <a
            href={links.webApp}
            className="text-sm font-medium text-[#697386] transition-colors hover:text-[#172033]"
          >
            Entrar
          </a>
          <a
            href={links.webApp}
            className="rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
          >
            Começar grátis
          </a>
        </div>

        <button
          type="button"
          className="rounded-lg border border-[#e4e8ef] px-3 py-2 text-sm text-[#172033] md:hidden"
          onClick={() => setMenuOpen((open) => !open)}
          aria-label="Abrir menu"
        >
          Menu
        </button>
      </div>

      {menuOpen && (
        <div className="border-t border-[#e4e8ef] bg-white px-4 py-4 md:hidden">
          <div className="flex flex-col gap-3">
            {navLinks.map((link) => (
              <a
                key={link.href}
                href={link.href}
                className="text-sm font-medium text-[#697386]"
                onClick={() => setMenuOpen(false)}
              >
                {link.label}
              </a>
            ))}
            <a
              href={links.webApp}
              className="rounded-lg bg-[#2f6fed] px-4 py-2.5 text-center text-sm font-bold text-white"
            >
              Começar grátis
            </a>
          </div>
        </div>
      )}
    </header>
  )
}
