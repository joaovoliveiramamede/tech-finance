# TechFinance Dashboard (Angular 15)

Cliente web do painel financeiro TechFinance Pessoal, construído com **Angular 15**, **Angular Router** e **Tailwind CSS**.

## Pré-requisitos

- Node.js 18+
- API Spring Boot rodando em `http://localhost:8080`

## Instalação

```bash
cd dashboard
npm install
```

## Desenvolvimento

```bash
npm run dev
```

Acesse: **http://localhost:4201**

O proxy em `proxy.conf.json` encaminha `/api` para a API local.

## Rotas

| Rota | Descrição |
|------|-----------|
| `/login` | Login |
| `/register` | Cadastro |
| `/criar-conta` | Onboarding — primeira conta |
| `/` | Dashboard (home) |
| `/contas` | Contas |
| `/transacoes` | Transações |
| `/categorias` | Categorias |

## Build

```bash
npm run build
```

Saída em `dist/dashboard/`.
