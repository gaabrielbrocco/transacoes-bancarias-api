# Transações Bancárias

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white) 
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

## Desenvolvido por
![Gabriel Brocco de Oliveira](https://img.shields.io/badge/Gabriel%20Brocco%20de%20Oliveira-000000?style=for-the-badge)

## Descrição

O **Transações Bancárias** é uma aplicação desenvolvida em Java com o framework Spring Boot, com o objetivo de simular transações bancárias e simular o funcionamento de uma Blockchain. Com envio de e-mails e histórico de movimentações.

## Funcionalidades

- Cadastro de Usuários.
- Criação de contas vinculadas a um Usuário.
- Realizar transferências de valores entre contas.
- Histórico de movimentações.
- Envio de e-mails para os usuários.
- Simulação de uma Blockchain.


## Configuração do Ambiente

### Instalação e Execução

1. **Clone o repositório**:

   ```bash
   git clone git@github.com:gaabrielbrocco/transacoes-bancarias-api.git
   cd transacoes-bancarias-api

2. **Configure os arquivos .env com seus dados**:

   ```bash
   .env

3. **Execute a aplicação com o Docker**:

   ```bash
   docker compose up --build -d
   
4. **Acesse a api no endereço**:

   ```bash
   http://localhost:8080
   
### Documentação

A documentação ficará disponível em: http://localhost:8080/swagger-ui/index.html
