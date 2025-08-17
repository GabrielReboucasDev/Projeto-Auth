# Auth

## Descrição
**Auth** é um projeto desenvolvido para autenticação e envio de uma lista de funcionários de uma empresa fictícia.

Este projeto foi desenvolvido para aprimorar os conhecimentos em:
- **Web API Rest**
- **Autenticação JWT**
- **Spring Security**

## Autores
- **Gabriel Sousa Rebouças Freire**

## Tecnologias Utilizadas
- **Java 17** (Spring Boot)
- **Spring Security**
- **JWT (JSON Web Token)**
- **Banco de dados H2 (em memória)**
- **Springdoc OpenAPI** (para documentação da API) / Swagger

## Estrutura do Projeto
O projeto segue a estrutura do **Spring Boot**, com as seguintes dependências principais:
- **Spring Boot Starter Web** – Para criação da API REST.
- **Spring Boot Starter Data JPA** – Para acesso ao banco de dados.
- **Spring Boot Starter Security** – Para gerenciamento de autenticação e segurança.
- **Springdoc OpenAPI** – Para documentação da API.
- **JWT** – Para geração e validação de tokens JWT.
- **Banco H2** – Banco de dados em memória.
- **Lombok** – Para redução de código boilerplate.

## Como Utilizar
- Criar um novo usuário: POST http://localhost:8080/Auth/usuarios/criarUsuario
- Realizar Login: POST http://localhost:8080/Auth/auth/login
- Listar funcionários: GET http://localhost:8080/Auth/funcionarios/lista
