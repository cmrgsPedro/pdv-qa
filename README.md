# PDV QA

Projeto acadêmico da disciplina de Qualidade e Teste de Software, desenvolvido a partir de um fork do sistema [PDV](https://github.com/repo-software-testing-courses/pdv).

## Equipe

- Pedro Campos Camargos
- Arthur Jardim Antunes Ferreira
- Rodrigo Lima de Albuquerque
- Joao Paulo Alves da Silveira

## Objetivo

Planejar, projetar, executar e documentar testes de software sobre regras de negócio do PDV. O escopo inicial inclui vendas, caixa, recebimentos e regras de nota fiscal, com divisão de uma classe principal e uma funcionalidade manual por integrante.

## Estado atual

- Fork criado e colaboradores convidados.
- Ambiente Docker corrigido e executado.
- Login e acesso à tela principal validados.
- Plano de Testes inicial preparado.

## Execução com Docker

### Requisitos

- Git
- Docker Desktop com Docker Compose v2
- Portas `8080`, `3306` e `5005` disponíveis

### Inicialização

Na raiz do projeto, execute:

```sh
docker compose up --build
```

Quando a aplicação terminar de iniciar, acesse:

```text
http://localhost:8080
```

Credenciais iniciais:

```text
Usuário: gerente
Senha: 123
```

Para encerrar:

```sh
docker compose down
```

## Escopo inicial de testes

| Integrante | Classe candidata | Funcionalidade manual candidata |
|---|---|---|
| Pedro | `VendaService` | Realização e fechamento de venda |
| Arthur | `CaixaService` | Abertura e fechamento de caixa |
| Rodrigo | `RecebimentoService` | Recebimento e baixa de parcelas |
| João Paulo | `NotaFiscalItemService` | Regras de tributação de itens |

As complexidades ciclomáticas informadas no Plano de Testes são estimativas preliminares e deverão ser confirmadas com uma ferramenta de análise estática.

## Documentação

- [Plano de Testes](https://docs.google.com/document/d/1eTk-quTCE7s7sz2MA6DzukG59YJlWnpV/edit?usp=sharing&ouid=103012691689036529213&rtpof=true&sd=true)
- [Registro de uso de IA](docs/ai/AI-LOG.md)

## Repositórios

- Fork do grupo: <https://github.com/cmrgsPedro/pdv-qa>
- Projeto original: <https://github.com/repo-software-testing-courses/pdv>

## Tecnologias do sistema

- Java 8
- Spring Boot
- Thymeleaf
- MySQL 8
- Maven
- Flyway
- Docker e Docker Compose

