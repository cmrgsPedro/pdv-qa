# Execução consolidada dos testes unitários

Código analisado na master em [`84794fb`](https://github.com/cmrgsPedro/pdv-qa/tree/84794fbbdd3d9802ce0196ca968248dae84f4c21), em 23/11/2026. Maven em contêiner Java 8, sem iniciar a aplicação ou o banco.

| Classe | Aprovados | Ramos JaCoCo | Mutantes mortos no PIT |
|---|---:|---:|---:|
| `VendaService` | 33 | 38/38 (100,0%) | 60/66 (90,9%) |
| `CaixaService` | 27 | 43/44 (97,7%) | 52/54 (96,3%) |
| `RecebimentoService` | 27 | 33/34 (97,1%) | 37/39 (94,9%) |
| `NotaFiscalItemService` | 17 | 40/40 (100,0%) | 35/37 (94,6%) |

**104 testes aprovados, sem falhas, erros ou ignorados.** O PIT matou 184 de 196 mutantes, sem timeouts. Cada uma das quatro classes passou das metas de 80% para ramos e mutação.

- [Resumo Surefire](surefire.json)
- [Contadores JaCoCo](jacoco.csv)
- [Relatório de mutantes do PIT](pitest-mutations.xml)

Execução reproduzível a partir do módulo `pdv` em Java 8:

```sh
mvn -B -ntp test org.pitest:pitest-maven:mutationCoverage "-Dtest=VendaServiceTest,CaixaServiceTest,RecebimentoServiceTest,NotaFiscalItemServiceTest" -DtimeoutConst=20000 -Dthreads=2
```
