# Mustang - Backend API

Mustang é uma API REST desenvolvida em Spring Boot 3 que segue os princípios do SOLID. Este projeto é construído com Java 17 e pode ser facilmente executado usando Docker Compose.

## Tecnologias Utilizadas

- **Spring Boot 3**
- **Java 17**
- **Princípios SOLID**
- **Docker**

## Pré-requisitos

- **Java 17**: Certifique-se de ter o Java 17 instalado.
- **Maven**: Para construir e testar o projeto localmente.
- **Docker**: Para executar o projeto usando Docker Compose.

## Configuração

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/mustang-backend.git
cd mustang-backend

```

### 2. Configurar as Credenciais do Banco de Dados

```bash
spring.datasource.url=jdbc:mysql://seu_host:3306/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

```

### 3. Executando a Aplicação

voce pode executar pelo docker compose utilizando o comando pelo terminal na pasta raiz do projeto

```bash
docker-compose up

```

ou pode rodar o arquivo aplication dentro do packge principal

### 4. Documentação dos Endpoints

os endpoints estão documentados no swagger, as rodas estão projegidas e requerem um JWT que voce consegue fazendo um request para o endpoint de login e depois colando em autorized no topo da pagina do swagger

link do swagger : http://localhost:8080/swagger-ui/index.html#/
