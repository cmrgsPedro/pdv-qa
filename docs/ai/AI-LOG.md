# Registro de Uso de Inteligência Artificial

Este arquivo registra o uso de ferramentas de inteligência artificial no trabalho, as decisões tomadas pela equipe e as formas de validação aplicadas. Os nomes dos responsáveis devem ser completados antes da entrega.

## Interação 001 Seleção do projeto

- **Data:** 19/09/2026
- **Responsável:** Pedro Camargos
- **Ferramenta:** ChatGPT
- **Atividade:** Análise comparativa dos repositórios disponibilizados pela professora.
- **Prompt resumido:** Analisar os repositórios da organização `repo-software-testing-courses` e indicar o projeto mais adequado aos requisitos do trabalho.
- **Resultado obtido:** O PDV foi recomendado por ser uma aplicação web Java com regras de negócio, interface adequada a Selenium e classes candidatas para testes unitários.
- **Decisão da equipe:** Selecionar o PDV e testar as funcionalidades do sistema.
- **Validação:** O grupo confirmou que a aplicação possuía as tecnologias e funcionalidades descritas.

## Interação 002 Avaliação das classes candidatas

- **Data:** 19/09/2026
- **Responsável:** Arthur Jardim
- **Ferramenta:** ChatGPT
- **Atividade:** Identificação inicial de classes não CRUD com regras de negócio.
- **Prompt resumido:** Identificar quatro classes adequadas para a divisão do trabalho entre os integrantes.
- **Resultado obtido:** Foram sugeridas `VendaService`, `CaixaService`, `RecebimentoService` e `NotaFiscalItemService`.
- **Decisão da equipe:** Adotar as classes como candidatas iniciais.
- **Validação:** A equipe inspecionará o código e confirmará a complexidade ciclomática com uma ferramenta de análise estática antes da implementação definitiva dos testes.

## Interação 003 Preparação do ambiente Docker

- **Data:** 20/09/2026
- **Responsável:** Pedro Camargos
- **Ferramenta:** ChatGPT
- **Atividade:** Diagnóstico de falha na construção da imagem da aplicação.
- **Prompt resumido:** Analisar o erro `openjdk:8-jdk-slim: not found` apresentado por `docker compose up --build`.
- **Resultado obtido:** Foi identificada a indisponibilidade da imagem-base antiga e recomendada a imagem `maven:3.9.16-eclipse-temurin-8-noble`.
- **Decisão da equipe:** Atualizar somente o `Dockerfile`, preservando Java 8 e o código da aplicação.
- **Validação:** A imagem foi construída, os contêineres iniciaram e o sistema foi acessado em `http://localhost:8080`.

## Interação 004 Planejamento mínimo da Entrega 1

- **Data:** 20/09/2026
- **Responsável:** Arthur Jardim
- **Ferramenta:** ChatGPT
- **Atividade:** Organização dos artefatos prioritários para a apresentação.
- **Prompt resumido:** Separar os requisitos mínimos da Entrega 1 de atividades que poderiam ser realizadas posteriormente.
- **Resultado obtido:** Foram priorizados o Plano de Testes, a divisão das classes e funcionalidades, o projeto dos casos, o registro de IA e a apresentação.
- **Decisão da equipe:** Adiar cobertura, mutação, Selenium, integração e refatorações para as etapas correspondentes do trabalho.
- **Validação:** A equipe comparará o conteúdo produzido com o enunciado e com as orientações da professora.

