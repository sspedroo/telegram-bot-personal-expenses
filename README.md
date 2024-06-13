# Telegram bot para gastos pessoais

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![SQL](https://img.shields.io/badge/MySQL-%23316192.svg?style=for-the-badge&logo=MySQL&logoColor=white)
![Docker Badge](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff&style=for-the-badge)
![Telegram Badge](https://img.shields.io/badge/Telegram-26A5E4?logo=telegram&logoColor=fff&style=for-the-badge)

Projeto pessoal para praticar e criar um bot onde eu consiga registrar meus gastos com facilidade.

## Tecnologias
* Spring Boot
* Spring Data JPA
* Docker Compose
* Telegram API
* MySQL

## Como executar
* Clonar o repositório.
* Ir ao telegram e criar um bot de sua preferência, armazenar o token em um local seguro, criar no bot um **comando "\novo_gasto"**.
* Na aplicação, na classe **TransactionBot** colocar o token e a senha de sua escolha.
* Dar o comando **docker compose up** no terminal da aplicação. Nisso será criado o banco de dados. (Necessário ter o docker em sua maquina)
* Na classe **DemoApplication** executar o projeto, de inicio ele irá criar algumas formas de pagamento e algumas categorias.
* Ir no telegram e interagir com o Bot criado por você anteriomente.

## Outros
Por favor, qualquer critica construtiva, avaliação ou melhoria, por favor entre em contato comigo. Estou buscando evoluir sempre.
