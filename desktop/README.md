# Desktop TechFinance Pessoal

Este módulo contém a aplicação desktop JavaFX do sistema TechFinance Pessoal.

## Objetivo

Fornecer uma interface gráfica desktop para gerenciar finanças pessoais, usando a mesma base de domínio e regras de negócio do projeto modular.

## Tecnologias

- Java 17
- Maven
- JavaFX

## Estrutura do módulo

- `pom.xml` - configurações e dependências do módulo desktop
- `src/main/java` - código-fonte da aplicação desktop
- `src/main/resources` - recursos da aplicação, como arquivos de configuração e estilos

## Classe principal

- `com.techfinance.pessoal.desktop.DesktopApplication`

## Como executar

1. Abra o terminal na raiz do projeto (`tech-finance`).
2. Execute:

```bash
mvn -pl desktop javafx:run
```

Isso inicia o aplicativo JavaFX usando o módulo `desktop`.

## Observações

- O módulo desktop faz parte de uma arquitetura modular Maven que também inclui o módulo `api`.
- Futuramente pode compartilhar mais código de domínio e regras com outros módulos, como `web`.
