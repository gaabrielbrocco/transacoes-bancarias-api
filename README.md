# Transações Bancárias

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white) 
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-000000?style=for-the-badge&logo=grafana&logoColor=white)
![OpenTelemetry](https://img.shields.io/badge/OpenTelemetry-003B57?style=for-the-badge&logo=open-telemetry&logoColor=white)
![Loki](https://img.shields.io/badge/Loki-FF620D?style=for-the-badge&logo=loki&logoColor=white)
![Tempo](https://img.shields.io/badge/Tempo-00B7A6?style=for-the-badge&logo=tempo&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-FF1493?style=for-the-badge&logo=prometheus&logoColor=white)

## Desenvolvido por
![Gabriel Brocco de Oliveira](https://img.shields.io/badge/Gabriel%20Brocco%20de%20Oliveira-000000?style=for-the-badge)

## Descrição

O **Transações Bancárias** é uma aplicação desenvolvida em Java com o framework Spring Boot, com o objetivo de simular transações bancárias e implementar o funcionamento de uma Blockchain. Com envio de e-mails e histórico de movimentações.
Além disso, possui Observabilidade de 𝙇𝙤𝙜𝙨, 𝙈𝙚́𝙩𝙧𝙞𝙘𝙖𝙨 𝙚 𝙏𝙧𝙖𝙘𝙚𝙨. Melhorando a monitorização e o desempenho do sistema. 

Com o uso das ferramentas 𝙂𝙧𝙖𝙛𝙖𝙣𝙖, 𝙇𝙤𝙠𝙞, 𝙏𝙚𝙢𝙥𝙤, 𝙊𝙥𝙚𝙣𝙏𝙚𝙡𝙚𝙢𝙚𝙩𝙧𝙮 𝙚 𝙋𝙧𝙤𝙢𝙚𝙩𝙝𝙚𝙪𝙨, o projeto oferece insights em tempo real, permitindo uma análise profunda do comportamento do sistema e facilitando a detecção de falhas e gargalos.

## Funcionalidades

- Cadastro de Usuários.
- Criação de contas vinculadas a um Usuário.
- Realizar transferências de valores entre contas.
- Histórico de movimentações.
- Envio de e-mails para os usuários.
- Transações em Blockchain.
- Observabilidade de 𝙇𝙤𝙜𝙨, 𝙈𝙚́𝙩𝙧𝙞𝙘𝙖𝙨 𝙚 𝙏𝙧𝙖𝙘𝙚𝙨.


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
