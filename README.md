# DBank API

API REST desenvolvida em Java utilizando Spring Boot para simular operações de um banco digital.

## Funcionalidades

* Cadastro de clientes
* Criação automática de conta corrente
* Autenticação via JWT
* Transferência entre contas
* Consulta de transações por conta
* Controle de idempotência para operações financeiras
* Persistência em PostgreSQL
* Testes unitários e de integração

---

# Tecnologias Utilizadas

* Java 21
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* PostgreSQL
* Docker
* Docker Compose
* Gradle
* JUnit 5
* Mockito
* Lombok

---

# Arquitetura

O projeto foi desenvolvido seguindo princípios de:

* Clean Architecture
* SOLID
* Domain Driven Design (DDD)

Estrutura principal:

```text
src
├── main
│   ├── java
│   │   └── br.com.dbank
│   │       ├── domain
│   │       ├── application
│   │       ├── adapters
│   │       └── config
│   └── resources
│
└── test
```

---

# Pré-requisitos

Instalar:

* JDK 21
* Docker
* Docker Compose
* Git

Verificar instalações:

```bash
java -version
docker --version
docker compose version
git --version
```

---

# Clonando o Projeto

```bash
git clone https://github.com/fabianoizabel/dbank.git

cd dbank-backenb
```

---

# Subindo o PostgreSQL com Docker

Executar:

```bash
docker compose up -d
```

Verificar container:

```bash
docker ps
```

Resultado esperado:

```text
postgres-db
```

---

# Arquivo docker-compose.yml

```yaml
services:
  db:
    image: postgres:15-alpine
    container_name: dbank_postgres
    restart: always
    environment:
      POSTGRES_USER: user_dbank
      POSTGRES_PASSWORD: dbank123
      POSTGRES_DB: dbank
    ports:
      - "5432:5432"
    volumes:
      - dbank_postgres_data:/var/lib/postgresql/data
  kafdrop:
    container_name: dbank_kafkadrop
    image: obsidiandynamics/kafdrop
    restart: "no"
    ports:
       - "9000:9000"
    environment:
       KAFKA_BROKERCONNECT: "kafka:29092"
    depends_on:
       - "kafka"
  kafka:
    container_name: dbank_kafka
    image: obsidiandynamics/kafka
    restart: "no"
    ports:
       - "2181:2181"
       - "9092:9092"
    environment:
       KAFKA_LISTENER: "INTERNAL://:29092,EXTERNAL://:9092"
       KAFKA_ADVERTISED_LISTENERS: "INTERNAL://kafka:29092,EXTERNAL://localhost:9092"
       KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: "INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT"
       KAFKA_INTER_BROKER_LISTENER_NAME: "INTERNAL"
       KAFKA_ZOOKEEPER_SESSION_TIMEOUT: "6000"
       KAFKA_RESTART_ATTEMPTS: "10"
       KAFKA_RESTART_DELAY: "5"
       ZOOKEPER_AUTOPURGE_PURGE_INTERVAL: "0"

volumes:
    dbank_postgres_data:
```

---

# Configuração da Aplicação

Arquivo:

```text
src/main/resources/application.yml
```

```yaml
server:
  port: 8080

dbank:
  api:
     secret:
        key: u844sTMt0mFkDiFMZ7bafMz4ZXal9tStoQ15VNPoDXG
  business:
     default:
        agency: 326

spring:
  application:
    name: dbank-backend
  
  datasource:
     postgres:
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://localhost:5432/dbank?currentSchema=dbank
        username: user_dbank
        password: dbank123
        hikari:
          maximum-pool-size: 5
          minimum-idle: 2
          connection-timeout: 30000
```

---

# Build da Aplicação

Linux/Mac:

```bash
./gradlew clean build
```

Windows:

```bash
gradlew.bat clean build
```

---

# Executando os Testes

Linux/Mac:

```bash
./gradlew test
```

Windows:

```bash
gradlew.bat test
```

---

# Executando a Aplicação

Linux/Mac:

```bash
./gradlew bootRun
```

Windows:

```bash
gradlew.bat bootRun
```

Aplicação disponível em:

```text
http://localhost:8080
```

---

# Fluxo de Utilização

## 1. Gerar Token JWT

Endpoint:

```http
POST /token
```

Payload:

```json
{
    "document": "98765432109",
    "password": "dbanK@321"
}
```
(esse document e password pertencem a um cliente povoado na tabela pelo script flyway)

Resposta:

```json
{
  "token": "eyJhbGciOi..."
}
```

---

## 2. Criar Cliente

Endpoint:

```http
POST /clients
```
Payload:


Headers:

```http
Authorization: Bearer <TOKEN gerado no passo 1 do fluxo de utilização>
```

```json
{
    "name": "Vinícius Junior",
    "document": "012345678901",
    "email": "vini@dbank.com",
    "password": "senh@475"
}
```

Resposta:

```json
{
    "clientID": "3012c037-58c3-4b0e-bb7f-025a47d9a92f",
    "name": "Vinícius Junior",
    "document": "012345678901",
    "email": "vini@dbank.com",
    "createdAt": "2026-06-22T18:08:28.3613215-03:00",
    "updatedAt": "2026-06-22T18:08:28.3613215-03:00"
}
```

---

## 3. Realizar Transferência

Endpoint:

```http
POST /transfers
```

Headers:

```http
Authorization: Bearer <TOKEN gerado no passo 1 do fluxo de utilização>
```

Payload:

```json
{
    "idempotencyKey": "4d7d8820-72b3-4e61-bab8-b9d64d6fa621",
    "sourceAccountID": "6d85c74b-c79b-4723-a289-146977609453",
    "destinationAccountID": "bba7e70d-db34-41c2-bc61-e4ff3953cd60",
    "amount": 20.28
}
```
(contas povoadas na tabela pelo script flyway)

Resposta:

```json
{
    "transactionID": null,
    "idempotencyID": "4d7d8820-72b3-4e61-bab8-b9d64d6fa621",
    "sourceAccountID": "6d85c74b-c79b-4723-a289-146977609453",
    "destinationAccountID": "bba7e70d-db34-41c2-bc61-e4ff3953cd60",
    "description": "Transferencia entre contas [04254 para 30021]",
    "type": "TRANSFER",
    "amount": 20.28,
    "createdAt": "2026-06-22T15:16:07.2234739-03:00",
    "updatedAt": "2026-06-22T15:16:07.2234739-03:00"
}
```

---

## 4. Consultar Transações

Endpoint:

```http
GET /accounts/{accountId}/transactions
```

Headers:

```http
Authorization: Bearer <TOKEN gerado no passo 1 do fluxo de utilização>
```

Resposta:

```json
[
  {
    "transactionId": "987654",
    "type": "TRANSFER",
    "amount": 200.00
  }
]
```

---

# Controle de Idempotência

Operações financeiras exigem o envio do cabeçalho:

```http
Idempotency-Key
```

Exemplo:

```http
Idempotency-Key:
fcd7d5e6-c5f9-49e4-aed2-7fd743ddfc77
```

Caso uma mesma requisição seja enviada novamente com a mesma chave, a API retornará a resposta previamente registrada sem reprocessar a operação financeira.

---

# Segurança

A API utiliza:

* JWT Authentication
* Spring Security
* Senhas criptografadas com BCrypt
* Endpoints protegidos por Bearer Token

---

# Executando Localmente via IntelliJ

1. Importar projeto Gradle
2. Aguardar download das dependências
3. Executar:

```text
DBankApplication
```

4. Garantir que o container PostgreSQL esteja ativo

---

# Monitoramento do Banco

Acessar:

```bash
docker exec -it postgres-db psql -U dbank -d dbank
```

Listar tabelas:

```sql
\dt
```

Consultar clientes:

```sql
select * from tb_client;
```

Consultar contas:

```sql
select * from tb_account;
```

Consultar transações:

```sql
select * from tb_transaction;
```

---

# Executando Todos os Testes

```bash
./gradlew clean test
```

Relatório:

```text
build/reports/tests/test/index.html
```

---

# Considerações Arquiteturais

A solução implementa:

* Clean Architecture
* Casos de uso independentes
* Regras de negócio concentradas no domínio
* Controle de concorrência via lock pessimista
* Idempotência para operações financeiras
* Persistência transacional utilizando PostgreSQL
* Testes unitários e de integração

---

# Autor

Fabiano Izabel
