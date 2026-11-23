# Inspeção de código do PDV

Em 23/11/2026, o SonarQube Community Build `26.11.0.134175` analisou o módulo `pdv` no commit [`eee1cc9`](https://github.com/cmrgsPedro/pdv-qa/tree/eee1cc9907d6ff8a0d3bda61d8988c93465c86ab). Os 104 testes unitários selecionados passaram e o Quality Gate do projeto ficou `OK`. A análise encontrou **29 apontamentos nas quatro classes de produção selecionadas**. A [exportação da API do SonarQube](sonar-antes.json) registra os resultados por classe, os identificadores dos apontamentos e as linhas no commit analisado.

A visão geral mostra a cobertura agregada do módulo. A cobertura de ramos exigida para as quatro classes é medida por classe no relatório JaCoCo. [Captura da visão geral](sonar-antes-visao-geral.png) · [Relatório JaCoCo](../unitarios-entrega-2/README.md)

## Apontamentos por responsável

| Responsável | Classe | Resultado inicial | Correção proposta |
|---|---|---|---|
| Pedro Campos Camargos | [VendaService](sonar-antes-VendaService.png) | 11 ocorrências de `java:S6813`, linhas 40 a 70 | Declarar as dependências no construtor e manter as regras de venda. |
| Arthur Jardim Antunes Ferreira | [CaixaService](sonar-antes-CaixaService.png) | 3 ocorrências de `java:S6813`, linhas 32, 35 e 38 | Declarar `CaixaRepository`, `UsuarioService` e `CaixaLancamentoService` no construtor. |
| Rodrigo Lima de Albuquerque | [RecebimentoService](sonar-antes-RecebimentoService.png) | 9 ocorrências de `java:S6813`, linhas 33 a 57, e 1 de `java:S4973`, linha 72 | Comparar os IDs pelo valor e verificar o caso de dois `Long` distintos com o mesmo valor. Migrar a injeção para o construtor no mesmo PR ou em uma mudança separada. |
| João Paulo Alves da Silveira | [NotaFiscalItemService](sonar-antes-NotaFiscalItemService.png) | 5 ocorrências de `java:S6813`, linhas 27 a 39 | Declarar as cinco dependências no construtor e preservar o cálculo fiscal. |

Use a regra [`java:S6813`](https://rules.sonarsource.com/java/RSPEC-6813/) para deixar as dependências obrigatórias explícitas no construtor. Quem alterar a injeção deve verificar, além dos unitários, que o contexto Spring continua subindo. O teste unitário isolado não valida a criação dos beans pelo Spring.

Em `RecebimentoService.abrirRecebimento`, a regra [`java:S4973`](https://rules.sonarsource.com/java/RSPEC-4973/) aponta a comparação de dois `Long` com `!=`. Objetos distintos podem representar o mesmo ID. O caso de regressão deve confirmar que a parcela do cliente é aceita mesmo quando as referências dos IDs diferem e que um ID de outro cliente continua sendo rejeitado.

## Validação local das correções propostas

A segunda rodada analisou as alterações de código e testes combinadas dos quatro PRs sobre `eee1cc9`, antes de qualquer merge. Os **105 testes unitários passaram** e as quatro classes ficaram com **zero apontamentos abertos**. O SonarQube marcou os mesmos 29 apontamentos da rodada inicial como `FIXED` e o Quality Gate ficou `OK`. A [exportação do depois](sonar-depois.json) registra o SHA-256 do diff do código e dos testes, os resultados por arquivo e os identificadores corrigidos. [Captura da visão geral do depois](sonar-depois-visao-geral.png)

| Classe | Antes | Depois local | Captura dos corrigidos | PR |
|---|---:|---:|---|---|
| `VendaService` | 11 | 0 | [VendaService](sonar-depois-VendaService.png) | [#27](https://github.com/cmrgsPedro/pdv-qa/pull/27) |
| `CaixaService` | 3 | 0 | [CaixaService](sonar-depois-CaixaService.png) | [#30](https://github.com/cmrgsPedro/pdv-qa/pull/30) |
| `RecebimentoService` | 10 | 0 | [RecebimentoService](sonar-depois-RecebimentoService.png) | [#29](https://github.com/cmrgsPedro/pdv-qa/pull/29) |
| `NotaFiscalItemService` | 5 | 0 | [NotaFiscalItemService](sonar-depois-NotaFiscalItemService.png) | [#28](https://github.com/cmrgsPedro/pdv-qa/pull/28) |

Pedro Camargos solicitou as alterações, preparadas com assistência do Codex. Os responsáveis indicados no relatório são os responsáveis pelas classes. As revisões individuais são registradas no GitHub.

## Reprodução

Na pasta `pdv`, com JDK 21, Maven e um token de análise na variável `SONAR_TOKEN`, execute sobre o commit que será registrado no relatório:

```powershell
$env:SONAR_HOST_URL = 'http://127.0.0.1:9000'
$commit = git rev-parse HEAD
$java = (Get-Command java).Source
mvn -B clean test `
  org.sonarsource.scanner.maven:sonar-maven-plugin:5.5.0.6356:sonar `
  "-Dtest=VendaServiceTest,CaixaServiceTest,RecebimentoServiceTest,NotaFiscalItemServiceTest" `
  "-Dsonar.projectKey=pdv-qa" `
  "-Dsonar.buildString=$commit" `
  "-Dsonar.scm.disabled=true" `
  "-Dsonar.scanner.skipJreProvisioning=true" `
  "-Dsonar.scanner.javaExePath=$java"
```

A análise inicial usou uma cópia exata do código versionado, sem metadados do Git para o scanner. O scanner usou o JDK 21 local depois que a inicialização do JRE baixado automaticamente falhou. O módulo manteve o alvo Java 8 configurado no `pom.xml`.

Após integrar os PRs, confirmar o resultado na `master` com o mesmo projeto e perfil de regras. Acrescentar o link do relatório editável no README principal.
