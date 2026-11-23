# Registro de Uso de Inteligência Artificial

Este arquivo registra o uso de ferramentas de inteligência artificial no trabalho, as decisões tomadas pela equipe e as formas de validação aplicadas. As respostas da IA foram tratadas como apoio: o código, os comandos, os resultados e as alterações no repositório foram revisados pela equipe antes da integração.

## Interação 001 — Seleção do projeto

- **Data:** 19/09/2026
- **Responsável:** Pedro Campos Camargos
- **Ferramenta:** ChatGPT
- **Atividade:** Análise comparativa dos repositórios disponibilizados pela professora.
- **Prompt resumido:** Analisar os repositórios da organização `repo-software-testing-courses` e indicar o projeto mais adequado aos requisitos do trabalho.
- **Resultado obtido:** O PDV foi recomendado por ser uma aplicação web Java com regras de negócio, interface adequada a testes funcionais e classes candidatas para testes unitários.
- **Decisão da equipe:** Selecionar o PDV como sistema sob teste e criar o fork `cmrgsPedro/pdv-qa`.
- **Validação:** A equipe confirmou no código-fonte e na execução local que a aplicação possuía as tecnologias e funcionalidades identificadas.

## Interação 002 — Avaliação e divisão das classes

- **Data:** 19/09/2026
- **Responsável:** Arthur Jardim Antunes Ferreira
- **Ferramenta:** ChatGPT
- **Atividade:** Identificação de classes não CRUD com regras de negócio.
- **Prompt resumido:** Identificar quatro classes adequadas para a divisão do trabalho entre os integrantes.
- **Resultado obtido:** Foram sugeridas `VendaService`, `CaixaService`, `RecebimentoService` e `NotaFiscalItemService`.
- **Decisão da equipe:** Dividir as classes entre Pedro, Arthur, Rodrigo e João Paulo, respectivamente.
- **Validação:** O grupo inspecionou os métodos e as dependências das quatro classes e confirmou a existência de validações, decisões e efeitos colaterais relevantes. A medição formal de complexidade ciclomática ainda deverá ser registrada quando executada.

## Interação 003 — Preparação do ambiente Docker

- **Data:** 20/09/2026
- **Responsável:** Pedro Campos Camargos
- **Ferramenta:** ChatGPT
- **Atividade:** Diagnóstico de falha na construção da imagem da aplicação.
- **Prompt resumido:** Analisar o erro `openjdk:8-jdk-slim: not found` apresentado por `docker compose up --build`.
- **Resultado obtido:** Foi identificada a indisponibilidade da imagem-base antiga e recomendada uma imagem Maven com Eclipse Temurin 8.
- **Decisão da equipe:** Atualizar somente o ambiente de construção, preservando Java 8 e o código de produção.
- **Validação:** A imagem foi construída, os contêineres iniciaram e o sistema foi acessado localmente em `http://localhost:8080`. A correção foi integrada pelo PR `#1`.

## Interação 004 — Planejamento inicial da Entrega 1

- **Data:** 20/09/2026
- **Responsável:** Arthur Jardim Antunes Ferreira
- **Ferramenta:** ChatGPT
- **Atividade:** Organização dos artefatos prioritários para a primeira entrega.
- **Prompt resumido:** Separar os requisitos mínimos da Entrega 1 das atividades previstas para etapas posteriores.
- **Resultado obtido:** Foram priorizados o Plano de Testes, a divisão das classes e funcionalidades, os casos de teste, o registro de IA e a apresentação.
- **Decisão da equipe:** Preparar inicialmente a documentação e, após consultar a experiência de uma turma anterior, incluir também a implementação dos testes unitários em JUnit e Mockito.
- **Validação:** A estrutura inicial da entrega foi revisada e integrada pelo PR `#2`.

## Interação 005 — Análise do `pom.xml` e escolha das tecnologias de teste

- **Data:** 20/09/2026
- **Responsável:** Arthur Jardim Antunes Ferreira, Pedro Campos Camargos
- **Ferramenta:** ChatGPT
- **Atividade:** Verificação das versões e dependências adequadas para testes unitários.
- **Prompt resumido:** Analisar principalmente o `pom.xml` e recomendar tecnologias compatíveis com o projeto, sem criar testes de integração.
- **Resultado obtido:** Foram mantidos Java 8, JUnit 4 e `spring-boot-starter-test`. Foi identificada incompatibilidade do Mockito transitivo antigo com JDKs atuais.
- **Decisão da equipe:** Fixar `mockito.version` em `3.12.4` e `byte-buddy.version` em `1.11.13`, sem iniciar o contexto Spring e sem acessar banco de dados.
- **Validação:** A alteração foi revisada e integrada à `master` pelo PR `#3` (`build: atualiza dependencias dos testes unitarios`).

## Interação 006 — Geração das suítes unitárias

- **Data:** 20/09/2026
- **Responsável:** Pedro Campos Camargos, Arthur Jardim Antunes Ferreira, Rodrigo Lima de Albuquerque e João Paulo Alves da Silveira
- **Ferramenta:** ChatGPT, Claude Code
- **Atividade:** Elaboração de testes unitários isolados para as quatro classes selecionadas.
- **Prompt resumido:** Criar testes somente unitários, utilizando JUnit 4 e Mockito, cobrindo os comportamentos das classes sem alterar o código de produção.
- **Resultado obtido:** Foram preparados 88 métodos de teste: 28 para `VendaService`, 22 para `CaixaService`, 21 para `RecebimentoService` e 17 para `NotaFiscalItemService`.
- **Decisão da equipe:** Manter uma suíte por integrante e simular repositórios e serviços externos com Mockito. O teste existente `PdvApplicationTests`, baseado em `@SpringBootTest`, não foi considerado parte da suíte unitária isolada.
- **Validação:** Os arquivos foram conferidos contra as assinaturas reais das classes e executados no ambiente de preparação antes da distribuição à equipe.

## Interação 007 — Cobertura e limitações estruturais

- **Data:** 20/09/2026
- **Responsável:** João Paulo Alves da Silveira
- **Ferramenta:** Claude Code
- **Atividade:** Execução das suítes e análise do relatório JaCoCo.
- **Prompt resumido:** Executar os testes, medir a cobertura das quatro classes e explicar qualquer trecho que não pudesse ser exercitado unitariamente.
- **Resultado obtido:** Na validação de preparação, os 88 testes foram aprovados, com 100% de linhas, instruções e métodos nas quatro classes selecionadas.
- **Limitações identificadas:** Permaneceram duas decisões de branch estruturalmente inalcançáveis: uma condição redundante em `CaixaService`, limitada pelos valores do enum, e a verificação `codtitulo == null` em `RecebimentoService`, precedida pelo desempacotamento do `Long`.
- **Decisão da equipe:** Não modificar o código de produção apenas para aumentar a métrica de branches; registrar tecnicamente as limitações.
- **Validação:** As contagens de testes foram novamente confirmadas nos quatro arquivos presentes na `master`. A equipe deve conservar a saída da execução integrada final e o relatório JaCoCo gerado na versão entregue.

## Interação 008 — Diagnóstico da execução de `RecebimentoServiceTest`

- **Data:** 20/09/2026
- **Responsável:** Rodrigo Lima de Albuquerque
- **Ferramenta:** ChatGPT
- **Atividade:** Investigação de `NoClassDefFoundError: LPessoaService;` durante a execução do teste.
- **Prompt resumido:** Analisar uma execução em que apareciam duas classes chamadas `RecebimentoServiceTest`, uma delas no pacote incorreto `service.notafiscal`.
- **Resultado obtido:** A classe correta executou 21 testes sem falhas; a falha veio de um `.class` antigo existente em `target/`. A busca no código-fonte confirmou a existência de apenas um `RecebimentoServiceTest.java`.
- **Decisão da equipe:** Executar Maven com `clean` antes do teste e manter somente `src/test/java/net/originmobi/pdv/service/RecebimentoServiceTest.java`.
- **Validação:** O arquivo correto foi integrado pelo PR `#7`. Também foi orientado colocar o parâmetro `-Dtest` entre aspas no PowerShell quando houver uma lista separada por vírgulas.

## Interação 009 — Teste de mutação de `NotaFiscalItemService`

- **Data:** 22/11/2026
- **Responsável:** João Paulo Alves da Silveira
- **Ferramenta:** Claude Code
- **Atividade:** Execução do PIT sobre `NotaFiscalItemService` e análise dos mutantes sobreviventes da suíte `NotaFiscalItemServiceTest`.
- **Prompt resumido:** Analisar o arquivo de teste de `NotaFiscalItemService`, identificar os mutantes sobreviventes e eliminá-los.
- **Resultado obtido:** A execução inicial gerou 37 mutantes, dos quais 33 foram mortos (89% de força de teste), com 100% de cobertura de linhas. Sobreviveram quatro:
  - linhas 110 e 128 (`VoidMethodCallMutator`): remoção de `System.out.println(e)` nos blocos `catch` de `insere` e `remove`. Os testes de falha verificavam apenas a mensagem da `RuntimeException` lançada, sem conferir o registro da exceção original;
  - linhas 160 e 169 (`MathMutator`): substituição de `aux = aux + 1` por `aux = aux - 1` em `verificaRegraDeTributacao`.
- **Alterações nos testes:** Os testes `informaFalhaAoSalvarItem` e `informaFalhaAoRemoverItem` passaram a capturar a saída padrão por meio do método auxiliar `capturaSaida` e a verificar que a mensagem da exceção original (`"falha ao salvar"` e `"falha ao remover"`) é registrada. A quantidade de métodos de teste permaneceu em 17.
- **Mutantes equivalentes:** Os mutantes das linhas 160 e 169 foram classificados como equivalentes. A variável `aux` é usada apenas na condição `aux == 0`; com a soma ela assume valores positivos e com a subtração, negativos, sendo zero somente quando não existe regra do tipo da nota nos dois casos. Não há entrada capaz de distinguir o mutante do código original.
- **Decisão da equipe:** Matar os mutantes dos `println` por meio de ajustes nos testes e registrar os dois mutantes de `aux` como equivalentes, sem alterar o código de produção apenas para aumentar a métrica. Uma eventual refatoração (substituir o contador por um `boolean` ou por `anyMatch`) eliminaria esses mutantes, mas ficou a critério do grupo.
- **Validação:** O PIT (`pitest-maven` 1.15.0, já configurado no `pom.xml`) foi executado novamente, restrito a `NotaFiscalItemService` e `NotaFiscalItemServiceTest`: 35 de 37 mutantes mortos (95% de força de teste), com os dois sobreviventes restantes sendo os equivalentes. Como o `mvnw` do módulo `pdv` não possui a pasta `.mvn`, a execução foi feita com uma instalação local do Maven 3.5.2. Comando utilizado: `mvn test-compile org.pitest:pitest-maven:mutationCoverage -DtargetClasses=net.originmobi.pdv.service.notafiscal.NotaFiscalItemService -DtargetTests=net.originmobi.pdv.service.notafiscal.NotaFiscalItemServiceTest`. O relatório é gerado em `pdv/target/pit-reports/`.

## Interação 010 — Configuração de JaCoCo e Pitest

- **Data:** 22/11/2026
- **Responsável:** Arthur Jardim Antunes Ferreira
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Preparar a medição de cobertura (JaCoCo) e de mutação (Pitest) e corrigir falhas de execução no Windows.
- **Prompt resumido:** Revisar o `pom.xml` e indicar como medir cobertura e escore de mutação.
- **Resultado obtido:** JaCoCo 0.8.11 e Pitest 1.15.0 configurados, dependência duplicada removida, Surefire fixado em 2.22.2 (a versão anterior encerrava a JVM com `Tests run: 0`) e `target/` ignorado no Git. A IA atribuiu a falha do Surefire, por engano, ao JDK e depois ao JaCoCo; a causa era a versão do plugin.
- **Decisão da equipe:** Rodar só as suítes `*ServiceTest`, pois o `PdvApplicationTests` exige MySQL.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Interação 011 — Melhoria do `CaixaServiceTest`

- **Data:** 22/11/2026
- **Responsável:** Arthur Jardim Antunes Ferreira
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Eliminar mutantes sobreviventes de `CaixaService`.
- **Prompt resumido:** Avaliar o relatório Pitest e escrever os testes que faltam, sem alterar o código de produção.
- **Resultado obtido:** 5 testes novos (22 → 27), com `ArgumentCaptor` para conferir os lançamentos. Escore de mutação de 91% para 96% (52 de 54). Os 2 mutantes restantes são equivalentes (`System.out.println`, linhas 73 e 74), a tratar no SonarQube. Defeito achado: `fechaCaixa` com código inexistente lança `NoSuchElementException` sem tratamento.
- **Decisão da equipe:** Não buscar 100%; documentar os mutantes equivalentes.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Interação 012 — Melhoria do `RecebimentoServiceTest`

- **Data:** 22/11/2026
- **Responsável:** Rodrigo Lima de Albuquerque
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Reforçar a suíte de `RecebimentoService` a partir dos relatórios Pitest e JaCoCo.
- **Prompt resumido:** Melhorar a classe de teste para a segunda entrega.
- **Resultado obtido:** 6 testes novos (21 → 27). Escore de mutação de 85% para 95% (37 de 39). Os 2 sobreviventes são equivalentes (`<` / `<=` na linha 140 e `System.out.println` na 212). A branch `codtitulo == null` (linha 108) é inalcançável por causa do desempacotamento do `Long`. Defeito confirmado por teste: título nulo lança `NullPointerException`. Candidatos a confirmar: comparação de `Long` com `!=` (linha 72) e `Optional.get()` sem verificação.
- **Decisão da equipe:** Manter os testes existentes, só acrescentar novos; não alterar o código de produção para melhorar métricas.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Interação 013 — Melhoria do `VendaServiceTest`

- **Data:** 09/11/2026
- **Responsável:** Pedro Campos Camargos
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Eliminar mutantes sobreviventes de `VendaService`.
- **Prompt resumido:** Melhorar a classe de teste a partir do relatório de mutação.
- **Resultado obtido:** Partida de 50 de 66 mutantes mortos (76%). 5 testes novos (28 → 33): data de cadastro, forma de pagamento gravada, valor do recebimento gerado, numeração das parcelas a prazo e valor do lançamento em dinheiro. Escore após os testes: a projeção é cerca de 91%. Defeitos achados por leitura de código: desconto e acréscimo passados em ordem trocada a `aprazo` e `avistaDinheiro`, soma de parcelas com `vlParcelas[i]` em vez de `[aux]` e `Optional.get()` sem verificação.
- **Decisão da equipe:** Os novos testes usam desconto igual a acréscimo para não fixar o comportamento invertido.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Interação 014 — Testes de sistema (Selenium) dos casos manuais

- **Data:** 22/11/2026
- **Responsável:** Arthur Jardim Antunes Ferreira (infraestrutura e U1), Pedro Campos Camargos (U2), João Paulo Alves da Silveira (U3), Rodrigo Lima de Albuquerque (U4)
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Automatizar com Selenium os casos manuais U1 a U4 no `webapp-1`.
- **Prompt resumido:** Automatizar os testes manuais do PDF para rodar no `webapp-1`.
- **Resultado obtido:** Classe base `TestE2EBase` e quatro classes de teste, escritas a partir dos templates e dos dados iniciais da aplicação. Os testes de outro repositório, comparados antes, não correspondiam aos casos manuais. A primeira versão do U4 verificava o comportamento defeituoso do sistema e por isso passava; foi reescrita para verificar o comportamento esperado.
- **Decisão da equipe:** Passos que falharam no manual (#9 e #11) viram testes separados com a tag `defeito-conhecido`, fora da execução padrão. O #10 (e-mail não persistido) não reproduziu nem na automação nem na reexecução manual e deve ser reavaliado.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Interação 015 — Adicionando testes extras E2E

- **Data:** 22/11/2026
- **Responsável:** Arthur Jardim Antunes Ferreira
- **Ferramenta:** Claude (Claude Code)
- **Atividade:** Adicionar ao `webapp-1` três testes E2E.
- **Prompt resumido:** Adicionar alguns testes nesse repositório, escolhendo os mais úteis.
- **Resultado obtido:** `TestE2ESecurityRoles`, `TestE2ERegisterAndLogin`, adaptados à base `TestE2EBase`, com verificações mais fortes. A origem está citada no README e neste registro.
- **Validação:** Os integrantes revisaram o código dos testes, pediram uma revisão adicional à IA, executaram os testes e conferiram a saída do terminal e os relatórios e acompanharam a execução visualmente.

## Limites e responsabilidade da equipe

- A IA auxiliou na análise, geração inicial, revisão e diagnóstico, mas não substituiu a conferência do código-fonte nem a execução dos testes.
- Os testes gerados foram revisados e enviados em Pull Requests separados, preservando a responsabilidade de cada integrante.
- Métricas obtidas antes dos merges devem ser reproduzidas na `master` final antes de serem apresentadas como evidência definitiva.
- Alterações no código de produção não foram realizadas apenas para satisfazer métricas de cobertura.
