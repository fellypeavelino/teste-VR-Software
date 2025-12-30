# 📦 Sistema de Pedidos Assíncrono  
**Java + Spring Boot + Swing + RabbitMQ**

Este projeto implementa um **sistema de pedidos assíncrono**, composto por:

- 🖥️ **Aplicação Desktop Java Swing** (cliente)
- 🌐 **Backend Spring Boot** (API REST)
- 🐇 **RabbitMQ** para mensageria assíncrona
- 🐳 **Docker Compose** para infraestrutura

O fluxo simula o envio de pedidos, processamento assíncrono e retorno de status
(**SUCESSO** ou **FALHA**).

---

## 🧱 Arquitetura
┌──────────────┐ HTTP ┌────────────────┐
│ Java Swing │ ───────────────▶ │ Spring Boot │
│ (Cliente) │ │ API REST │
└──────────────┘ └───────┬────────┘
│
│ AMQP
▼
┌──────────────┐
│ RabbitMQ │
│ (Filas) │
└──────────────┘


---

## 🚀 Tecnologias Utilizadas

### Backend
- Java 17+
- Spring Boot
- Spring Web
- Spring AMQP
- Jackson (com suporte a `LocalDateTime`)
- RabbitMQ

### Desktop
- Java Swing
- Jackson Databind
- `java.net.http.HttpClient`
- Maven

### Infra
- Docker
- Docker Compose

---

## 📌 Funcionalidades

### Backend (Spring Boot)
- `POST /api/pedidos` — recebe pedidos
- Validação básica dos dados
- Publicação em fila RabbitMQ
- Processamento assíncrono com falha simulada (20%)
- **Dead Letter Queue (DLQ)** configurada
- Publicação de status (sucesso/falha)
- Status mantido em memória
- `GET /api/pedidos/status/{id}` — consulta status

### Cliente Desktop (Swing)
- Interface gráfica simples (JFrame)
- Envio de pedidos via HTTP
- Listagem de pedidos enviados
- Polling assíncrono para atualização de status
- Atualização segura da UI na EDT
- Tratamento de erros de comunicação

---

## 🐳 RabbitMQ (Docker)

## Acesso via browser
### Iniciar o docker primeiro 
- URL: http://localhost:15672
- Usuário: guest
- Senha: guest

### `docker-compose.yml`

```yaml
version: '3.8'

services:
  rabbitmq:
    image: rabbitmq:3.13-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
```

## Endpoints da API

A API expõe os seguintes endpoints para criação e acompanhamento de pedidos assíncronos.

---

### Pedidos

---

### Criar Pedido

- **POST `/api/pedidos`** – Cria um novo pedido e o envia para processamento assíncrono.

- **Headers:**
  - `Content-Type: application/json`

- **Payload:**
```json
    {
        "id": "9f1c2c4a-3a47-4a7c-b9c1-8f2d9c2b7e31",
        "produto": "Notebook Dell Inspiron",
        "quantidade": 2,
        "dataCriacao": "2025-12-29T16:30:00"
    }
```

## Campos obrigatórios:

- produto
- quantidade

## Resposta (HTTP 202 – Accepted):

```json
    {
        "id": "9f1c2c4a-3a47-4a7c-b9c1-8f2d9c2b7e31",
    }
```
## Consultar Status do Pedido

### GET /api/pedidos/status/{id} – Consulta o status atual de um pedido.

- Parâmetros de URL:
id (UUID do pedido)

- Exemplo de Requisição:
GET /api/pedidos/status/b0210710-0b18-46d0-9503-d8cde94b41ca
