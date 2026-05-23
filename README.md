# TechFinance Pessoal

Sistema financeiro pessoal desenvolvido em Java utilizando arquitetura modular com Maven.

O objetivo do projeto é unificar múltiplas aplicações utilizando o mesmo ecossistema de negócio:

- Desktop JavaFX
- API REST com Spring Boot
- Futuro módulo Web (JSF)
- Compartilhamento de domínio e regras de negócio
- Arquitetura escalável e modular

---

# Arquitetura do Projeto

O projeto utiliza Maven Multi-Modules.

Cada módulo possui uma responsabilidade específica.

```text
pessoal/
│
├── pom.xml
│
├── desktop/
│   ├── pom.xml
│   └── src/
│
└── api/
    ├── pom.xml
    └── src/
```

---

# Objetivo da Arquitetura

A ideia principal é separar aplicações diferentes utilizando o mesmo sistema base.

Exemplo:

```text
Mesmo sistema financeiro
        │
        ├── Aplicação Desktop (JavaFX)
        ├── API REST (Spring Boot)
        └── Aplicação Web (JSF futuramente)
```

Isso permite:

- reutilização de código
- separação de responsabilidades
- escalabilidade
- manutenção simplificada
- múltiplas interfaces usando o mesmo backend lógico

---

# Módulos

## Root Project (`pessoal`)

Projeto pai responsável por:

- gerenciar os módulos
- centralizar versões
- centralizar propriedades Maven
- controlar build geral

### Tecnologias

- Maven
- Java 17

### Tipo de empacotamento

```xml
<packaging>pom</packaging>
```

Isso significa que o módulo raiz NÃO gera `.jar`.

Ele apenas organiza os módulos filhos.

---

# Módulo Desktop (`desktop`)

Aplicação Desktop utilizando JavaFX.

## Responsabilidades

- Interface gráfica desktop
- Telas
- Navegação
- Componentes visuais
- Experiência do usuário

## Tecnologias

- JavaFX
- Maven

## Classe principal

```java
com.techfinance.pessoal.desktop.DesktopApplication
```

## Estrutura

```text
desktop/
│
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── techfinance/
                    └── pessoal/
                        └── desktop/
                            └── DesktopApplication.java
```

---

# Módulo API (`api`)

API REST utilizando Spring Boot.

## Responsabilidades

- endpoints REST
- autenticação
- integração
- regras HTTP
- comunicação frontend/backend

## Tecnologias

- Spring Boot
- Spring Web

## Classe principal

```java
com.techfinance.pessoal.api.ApiApplication
```

## Estrutura

```text
api/
│
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── techfinance/
                    └── pessoal/
                        └── api/
                            ├── ApiApplication.java
                            └── controller/
```

---

# Fluxo Futuro da Arquitetura

A arquitetura será expandida futuramente:

```text
pessoal/
│
├── domain/           -> entidades e regras puras
├── application/      -> casos de uso
├── infrastructure/   -> banco, JPA, Hibernate
├── desktop/          -> JavaFX
├── api/              -> Spring Boot
└── web/              -> JSF
```

---

# Tecnologias Utilizadas

| Tecnologia | Objetivo |
|---|---|
| Java 17 | Linguagem principal |
| Maven | Build e gerenciamento |
| JavaFX | Aplicação desktop |
| Spring Boot | API REST |
| Spring Web | Endpoints HTTP |
| Maven Multi Modules | Arquitetura modular |

---

# Como Executar

# Pré-requisitos

Instalar:

- Java 17+
- Maven 3.9+

Verificar instalação:

```bash
java -version
```

```bash
mvn -version
```

---

# Compilar Todo o Projeto

Na raiz:

```bash
mvn clean install
```

Isso irá:

- compilar todos os módulos
- baixar dependências
- gerar builds

---

# Executar Desktop JavaFX

Na raiz:

```bash
mvn -pl desktop javafx:run
```

---

# Executar API Spring Boot

Na raiz:

```bash
mvn -pl api spring-boot:run
```

---

# Testar API

Abrir no navegador:

```text
http://localhost:8080/health
```

Resposta esperada:

```text
API TechFinance funcionando!
```

---

# Conceito de Modularização

Cada módulo pode:

- possuir dependências próprias
- possuir ciclo de vida próprio
- ser executado separadamente
- compartilhar código futuramente

Isso permite criar:

```text
Desktop
Web
Mobile
API
Microservices
CLI
```

utilizando o mesmo ecossistema de negócio.

---

# Roadmap Futuro

## Desktop

- Login
- Dashboard
- Wallet
- Transações
- Investimentos
- Perfil

## API

- JWT
- Spring Security
- Hibernate
- MySQL
- Swagger
- Clean Architecture

## Web

- JSF
- PrimeFaces
- Jakarta EE

## Infraestrutura

- Docker
- Docker Compose
- CI/CD
- AWS
- Kubernetes

---

# Padrões e Arquitetura

O projeto pretende utilizar:

- SOLID
- Clean Architecture
- Hexagonal Architecture
- Domain Driven Design (DDD)
- Repository Pattern
- Service Layer Pattern
- Dependency Injection

---

# Objetivo Educacional

Este projeto também serve como estudo avançado de:

- Java Desktop
- Backend Java
- Arquitetura de Software
- Sistemas Distribuídos
- Modularização Maven
- UI/UX Desktop
- APIs REST
- Persistência de Dados

---

# Autor

Projeto desenvolvido por João Vitor Oliveira Mamede.