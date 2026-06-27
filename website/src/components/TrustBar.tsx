const items = [
  'Java 17',
  'Spring Boot',
  'React',
  'JavaFX',
  'MySQL',
  'JWT',
  'Arquitetura hexagonal',
]

export function TrustBar() {
  return (
    <section className="border-b border-[#e4e8ef] bg-[#fafbfc] py-8">
      <div className="mx-auto max-w-6xl px-4 lg:px-6">
        <p className="mb-4 text-center text-xs font-semibold tracking-wider text-[#697386] uppercase">
          Tecnologia sólida por trás do produto
        </p>
        <div className="flex flex-wrap items-center justify-center gap-3">
          {items.map((item) => (
            <span
              key={item}
              className="rounded-full border border-[#e4e8ef] bg-white px-4 py-2 text-sm font-medium text-[#172033]"
            >
              {item}
            </span>
          ))}
        </div>
      </div>
    </section>
  )
}
