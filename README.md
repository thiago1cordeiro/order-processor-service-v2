# Order Processor Service

## 📌 Visão Geral

Este projeto é uma API desenvolvida com **Java 21** e **Spring Boot**, utilizando **Flyway** para migração de banco de dados, **Swagger** para documentação, **PostgreSQL** como banco de dados principal.

A API também realiza consultas a um serviço externo mockado via **Beeceptor**, conforme solicitado no desafio.

---

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Flyway** (Migração de banco de dados)
- **PostgreSQL** (Banco de dados relacional)
- **Swagger** (Documentação da API)
- **Docker Compose** (Facilita a inicialização do ambiente)
- **Beeceptor** (API mockada para consultas externas)

---

## 🚀 Como Rodar o Projeto

### 1️⃣ Pré-requisitos

Certifique-se de ter os seguintes requisitos instalados:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://jdk.java.net/21/)
- [Gradle](https://gradle.org/)

### 2️⃣ Configuração do Banco de Dados

Antes de rodar a aplicação, é necessário iniciar o **PostgreSQL** via **Docker Compose**. Para isso, execute o seguinte comando na raiz do projeto:

```sh
docker-compose up -d
```

Isso iniciará os contêineres necessários para o funcionamento da API.

### 3️⃣ Variáveis de Ambiente

O projeto já possui variáveis configuradas no `application.yml`, mas abaixo estão as principais para referência:

```
DATABASE_HOST=localhost
DATABASE_NAME=admin_db
DATABASE_PORT=5432
DATABASE_PWD=admin
DATABASE_SCHEMA=orders
DATABASE_USR=admin
BEECEPTOR_BASEURL=https://distribuitioncenters.free.beeceptor.com
```

### 4️⃣ Iniciando a Aplicação

Com o banco de dados em execução, basta rodar a aplicação utilizando sua **IDE** ou diretamente pelo terminal.

> **Importante:** Antes de iniciar, certifique-se de que as variáveis de ambiente foram corretamente configuradas.

### 5️⃣ Acessando a API

Após iniciar a aplicação, utilize os seguintes recursos:

- **API Principal**: [`http://localhost:8080`](http://localhost:8080)
- **Swagger UI** (Documentação da API): [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 🔹 Endpoints Principais

### 1️⃣ Criar um Pedido (POST `/order`)

**Descrição:** Cria um novo pedido no sistema.

**Exemplo de Requisição:**

```json
{
    "numberOrder": "12345",
    "items": [
        {
            "itemId": 123
        }
    ]
}
```

**Respostas:**

- **201 Created** → Pedido criado com sucesso.
- **400 Bad Request** → Dados inválidos ou item não encontrado.
- **409 Conflict** → Número do pedido já existe.
- **500 Internal Server Error** → Erro interno no servidor.

### 2️⃣ Consultar um Pedido (GET `/order/{id_pedido}`)

**Descrição:** Obtém os detalhes de um pedido.

**Exemplo de Resposta:**

```json
{
  "id": 5,
  "numberOrder": "teste123",
  "items": [
    {
      "itemId": 123,
      "distributionCenter": "CD1"
    }
  ]
}
```

**Respostas:**

- **200 OK** → Pedido encontrado com sucesso.
- **404 Not Found** → Pedido não encontrado.
- **500 Internal Server Error** → Erro interno no servidor.

### 3️⃣ API Mockada (Beeceptor)

A API realiza consultas a um serviço externo para obter informações sobre os centros de distribuição.

- **URL do Mock:** [Beeceptor](https://distribuitioncenters.free.beeceptor.com)
- **Exemplo de Resposta:**

```json
{
  "distribuitionCenters": ["CD1", "CD2", "CD3", "CD4", "CD5"]
}
```

---

## 📌 Notas Importantes

- Certifique-se de que o **PostgreSQL**  estejam rodando antes de iniciar a aplicação.
- As configurações do banco de dados estão definidas no arquivo `application.yml`.
- O **Swagger UI** pode ser utilizado para testar os endpoints da API sem necessidade de ferramentas externas como Postman.
- A API utiliza o **Beeceptor** para simular um serviço externo de centros de distribuição.
    - Caso queira alterar esse serviço, modifique a variável de ambiente **BEECEPTOR_BASEURL** no arquivo [`application.yml`](application/src/main/resources/application.yml).
    - Você pode também modificar a implementação do cliente Feign no arquivo [`DistributionCenterClientImpl.java`](application/src/main/java/com/thiago/orderprocessor/client/DistributionCenterClientImpl.java).
    - **Observação:** O Beeceptor possui um limite de **50 requisições gratuitas**, após o qual as chamadas serão bloqueadas.

---

## 📌 Escalabilidade e Performance

Esta API foi projetada com foco em **escalabilidade e alto desempenho**. Atualmente, ela atende **cinco centros de distribuição**, mas sua arquitetura permite uma escalabilidade eficiente para **20 centros ou mais** no futuro.

### 🔹 Melhorias para Desempenho e Escalabilidade

Para suportar um alto volume de requisições e garantir um processamento eficiente, algumas otimizações e estratégias podem ser implementadas:

- **Cache com Redis** → Redução da latência e diminuição da carga no banco de dados ao armazenar pedidos frequentemente acessados.
- **Load Balancer** → Distribuição eficiente da carga entre os pods para evitar sobrecarga e melhorar o tempo de resposta.
- **Escalonamento Automático com Kubernetes** → Ajuste dinâmico do número de pods com base na utilização de memória e CPU, garantindo eficiência durante horários de pico.
- **Fila de Mensagens com RabbitMQ ou Kafka** → Processamento assíncrono de pedidos para evitar bloqueios e melhorar a experiência do usuário.
- **Uso de Banco de Dados Read-Replicas** → Separação entre operações de leitura e escrita para otimizar consultas de alto volume.

Com essas estratégias, a API será capaz de lidar com um crescimento exponencial sem comprometer o desempenho.

