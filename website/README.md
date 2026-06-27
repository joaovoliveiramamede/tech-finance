# TechFinance — Website

Site institucional e landing page do **TechFinance Pessoal**, criado para apresentar e vender o software.

## Stack

- React 19
- TypeScript
- Vite 8
- Tailwind CSS 4

## Executar

```bash
cd website
npm install
npm run dev
```

## Build

```bash
npm run build
npm run preview
```

## Configuração

Edite `.env`:

```env
VITE_WEB_APP_URL=http://localhost:5173
VITE_API_DOCS_URL=http://localhost:8080/swagger-ui.html
VITE_CONTACT_EMAIL=contato@techfinance.dev
```

- `VITE_WEB_APP_URL` — link para o app web
- `VITE_API_DOCS_URL` — Swagger da API
- `VITE_CONTACT_EMAIL` — e-mail comercial
