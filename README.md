# 💊 NexusDev Desktop - Sistema de Gestão para Distribuidora Farmacêutica

> Sistema desktop integrado de gestão desenvolvido para a **Distribuidora CFA Ltda.**

![Status](https://img.shields.io/badge/status-active-success.svg)
![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![License](https://img.shields.io/badge/license-Proprietary-red.svg)

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Segurança](#segurança)
- [Equipe](#equipe)

---

## 🎯 Sobre o Projeto

O **NexusDev Desktop** é uma aplicação de gestão desenvolvida especificamente para a **Distribuidora CFA Ltda.**, empresa atuante no setor farmacêutico que fornece medicamentos para farmácias, clínicas e hospitais. O sistema foi criado para substituir processos manuais por uma solução informatizada, centralizando o controle de estoque, compras, vendas, fornecedores e clientes em uma única plataforma.

Desenvolvido em Java com interface gráfica Swing, o projeto foi realizado pela equipe **NexusDev** como Projeto Integrador do curso técnico de Assistente de Desenvolvimento de Software Computacionais do **SENAC Rio Claro – SP (2025)**.

> *"Conectando ideias, desenvolvendo o futuro."*

### Objetivos

- Centralizar e automatizar os processos operacionais da distribuidora
- Garantir rastreabilidade completa do estoque de medicamentos
- Otimizar o processo de compras junto a laboratórios fornecedores
- Controlar vendas e emissão de notas fiscais para drogarias clientes
- Gerenciar funcionários com controle de acesso por cargo

---

## ✨ Funcionalidades

### Gestão Completa

#### 💊 Gestão de Medicamentos e Estoque
- Cadastro de medicamentos com código, descrição, data de validade, quantidade, valor de custo e valor de venda
- Alerta automático para medicamentos com validade inferior a 90 dias
- Busca de medicamentos por nome, código ou laboratório
- Controle de entrada e saída de estoque integrado às compras e vendas

#### 🔬 Gestão de Laboratórios (Fornecedores)
- Cadastro completo com Nome, CNPJ, Telefone, E-mail e Endereço
- Vínculo de medicamentos fornecidos por laboratório
- Inativação de fornecedores preservando histórico de operações

#### 🏪 Gestão de Drogarias (Clientes)
- Cadastro completo com dados fiscais (CNPJ) e informações de contato
- Inativação de clientes preservando histórico de operações

#### 🛒 Módulo de Compras
- Registro de compras junto a laboratórios fornecedores
- Nota fiscal de entrada com data, itens adquiridos e valor total
- Cancelamento de compras restrito ao administrador

#### 📈 Módulo de Vendas
- Registro de vendas para drogarias clientes
- Emissão de nota fiscal de saída com código, descrição, quantidade, valor unitário e valor total

#### 👥 Gestão de Funcionários
- Cadastro de funcionários com Nome, CPF, Telefone, E-mail, Endereço e cargo
- Login via e-mail ou CPF com senha criptografada
- Operações de cadastro, edição e exclusão restritas ao administrador

---

## 🛠️ Tecnologias

### Back-end
- **Java 17+** — Linguagem principal, garantindo portabilidade e robustez
- **JDBC (Java Database Connectivity)** — Comunicação entre a aplicação e o MySQL nas classes DAO
- **MySQL** — Banco de dados relacional para persistência e integridade dos dados
- **Maven** — Gerenciamento de dependências e automação de build
- **Spring Security Crypto (BCrypt)** — Criptografia segura de senhas

### Front-end / Interface
- **Java Swing** — Construção de toda a interface gráfica (janelas, formulários, tabelas e menus)
- **NetBeans GUI Builder** — Geração de formulários `.form` de forma visual

### Ferramentas e Infraestrutura
- **Git / GitHub** — Versionamento colaborativo do código-fonte
- **Apache NetBeans IDE** — Ambiente de desenvolvimento integrado
- **PowerEdge R760xs** — Servidor rack utilizado para hospedagem do MySQL em produção

---

## 🏗️ Arquitetura

O NexusDev Desktop utiliza o padrão arquitetural **MVC (Model-View-Controller)** com camada **DAO (Data Access Object)**:

```
┌─────────────┐
│   Usuário   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    View     │ (Janelas Swing / .form)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Controller │ (Lógica de negócio / Objetos)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│     DAO     │ (Acesso a dados via JDBC)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Database   │ (MySQL)
└─────────────┘
```

---

## 📦 Instalação

### Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- **Java Development Kit (JDK) 17** ou superior
- **Maven** instalado e configurado no PATH
- **MySQL 8.0+** rodando localmente
- (Opcional) **Apache NetBeans**, IntelliJ IDEA ou Eclipse

### Passo a Passo

#### 1. Clone ou baixe o repositório

```bash
git clone https://github.com/seu-usuario/nexusdev-desktop.git
```

#### 2. Configure o Banco de Dados

Crie o banco de dados no MySQL:

```sql
CREATE DATABASE nexusdev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Importe o script SQL disponível no pacote `BD`:

```bash
mysql -u root -p nexusdev < src/main/java/BD/setup.sql
```

#### 3. Configure as Credenciais

Edite a classe `Conexao` no pacote `BD` com suas credenciais:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/nexusdev";
private static final String USER = "root";
private static final String PASS = "sua_senha";
```

#### 4. Compile e execute

```bash
mvn clean install
```

Execute a aplicação a partir da classe principal (tela de Login).

> ⚠️ **Importante:** Altere a senha do administrador padrão após o primeiro acesso!

---

## 📁 Estrutura do Projeto

```text
src/main/java/
│
├── BD/                     # Banco de Dados
│   ├── Conexao.java       # Configuração e conexão com MySQL
│   └── setup.sql          # Script de criação das tabelas
│
├── DAO/                    # Data Access Objects
│   ├── FuncionarioDAO.java
│   ├── MedicamentoDAO.java
│   ├── LaboratorioDAO.java
│   ├── DrogariaDAO.java
│   ├── CompraDAO.java
│   └── VendaDAO.java
│
├── Janelas/                # Interfaces Gráficas (Views)
│   ├── Login.java / .form
│   ├── MenuPrincipal.java / .form
│   ├── CadastroMedicamento.java / .form
│   ├── CadastroLaboratorio.java / .form
│   ├── CadastroDrogaria.java / .form
│   ├── Compra.java / .form
│   ├── Venda.java / .form
│   └── GerenciarFuncionarios.java / .form
│
├── Model/                  # TableModels (modelos de tabelas Swing)
│   ├── MedicamentoModel.java
│   ├── LaboratorioModel.java
│   └── ...
│
└── Objetos/                # Entidades do Sistema
    ├── Funcionario.java
    ├── Medicamento.java
    ├── Laboratorio.java
    ├── DrogariaObjeto.java
    ├── Compra.java
    └── Venda.java
```

---

## 🔒 Segurança

### Implementações de Segurança

- ✅ **BCrypt Password Hashing** — Senhas nunca armazenadas em texto plano
- ✅ **Prepared Statements (JDBC)** — Proteção contra SQL Injection
- ✅ **Controle de Acesso por Cargo** — Operações sensíveis restritas ao administrador
- ✅ **Autenticação por E-mail ou CPF** — Identificação flexível e segura

### Permissões por Cargo

| Cargo | Permissões |
|---|---|
| **Administrador** | Acesso completo, incluindo gestão de funcionários e cancelamento de compras |
| **Funcionário** | Acesso a medicamentos, estoque, compras, vendas, laboratórios e drogarias |

---

## 👥 Equipe

Projeto desenvolvido pela equipe **NexusDev** — Turno Noite · SENAC Rio Claro – SP · 2025.

| Integrante | Função |
|---|---|
| **Andrey Munhoz** | Desenvolvedor |
| **Lucas Garcia Marega Pedro** | Desenvolvedor |
| **Luis Felipe Mathias Leite** | Desenvolvedor |
| **Mauricio Rogerio Gobato** | Desenvolvedor |

### Professores Orientadores

- Danilo Arantes da Silva
- Alexandre Lazzari
- Elias Antônio da Silva

---

## 📝 Licença

Este projeto é proprietário e foi desenvolvido exclusivamente para a **Distribuidora CFA Ltda.**

© 2025 NexusDev — Todos os direitos reservados.

---

<div align="center">

**Desenvolvido com 💙 pela equipe NexusDev**

*Projeto Integrador — Assistente de Desenvolvimento de Software Computacionais · SENAC Rio Claro – SP*

</div>
