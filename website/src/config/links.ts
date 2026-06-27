export const links = {
  webApp: import.meta.env.VITE_WEB_APP_URL ?? 'http://localhost:5173',
  apiDocs: import.meta.env.VITE_API_DOCS_URL ?? 'http://localhost:8080/swagger-ui.html',
  contactEmail:
    import.meta.env.VITE_CONTACT_EMAIL ?? 'contato@techfinance.dev',
}
