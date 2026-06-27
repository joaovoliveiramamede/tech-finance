import { Navbar } from './components/Navbar'
import { Hero } from './components/Hero'
import { TrustBar } from './components/TrustBar'
import { Features } from './components/Features'
import { HowItWorks } from './components/HowItWorks'
import { Platforms } from './components/Platforms'
import { ProductPreview } from './components/ProductPreview'
import { AnalyticsSection } from './components/AnalyticsSection'
import { Plans } from './components/Plans'
import { Faq } from './components/Faq'
import { CtaBanner } from './components/CtaBanner'
import { Footer } from './components/Footer'

export default function App() {
  return (
    <div className="bg-white">
      <Navbar />
      <main>
        <Hero />
        <TrustBar />
        <Features />
        <HowItWorks />
        <ProductPreview />
        <Platforms />
        <AnalyticsSection />
        <Plans />
        <Faq />
        <CtaBanner />
      </main>
      <Footer />
    </div>
  )
}
