# Execução de sistema com Selenium

Execuções de 23/11/2026 sobre [`84794fb`](https://github.com/cmrgsPedro/pdv-qa/tree/84794fbbdd3d9802ce0196ca968248dae84f4c21), com Java 8, Selenium 3 e Chrome 94. O Webapp-1 e o MySQL 5.7 rodaram em contêineres descartáveis numa rede interna.

| Execução | Testes | Aprovados | Falhas | Erros |
|---|---:|---:|---:|---:|
| Rodada original com seletor `TestE2E*` | 10 | 9 | 0 | 1 |
| Rodada com seleção explícita das seis classes da Entrega 2 | 9 | 9 | 0 | 0 |
| U4 com a tag `defeito-conhecido` | 3 | 1 | 1 | 1 |

Na rodada original, os casos U1, U2, U3, os três cenários regulares de U4, cadastro/ativação/login e os dois cenários de permissões passaram. O erro ocorreu exclusivamente no teste legado `TestE2EFront.checkCreateList`. Seu setup tentou abrir `http://localhost:4200/`, que não estava disponível nesta rodada, e o Selenium retornou `ERR_CONNECTION_REFUSED`.

Na nova rodada, a seleção explícita executou as seis classes documentadas para a Entrega 2. Os nove testes passaram. O teste legado permanece no repositório para seu ambiente original.

Na rodada separada do U4:

- [Issue #9](https://github.com/cmrgsPedro/pdv-qa/issues/9): tentativa de editar o nome devolveu `InvalidElementStateException`. O Surefire classificou o caso como erro.
- [Issue #10](https://github.com/cmrgsPedro/pdv-qa/issues/10): o teste passou. A falha de persistência de e-mail não se reproduziu neste ambiente.
- [Issue #11](https://github.com/cmrgsPedro/pdv-qa/issues/11): a asserção falhou porque a lista não mostrou controle para adicionar um item.

O [resumo Surefire](surefire.json) mantém o resultado e a identificação de cada caso. Foram omitidas propriedades do processo, saídas brutas e dados de sessão do navegador.

Com o Webapp-1 e o Selenium disponíveis, a partir de `webapp-1/TrackOrJargh`:

```powershell
mvn test "-Dtest=TestE2EU1VisualizarComentarios,TestE2EU2TrailerMaisAssistido,TestE2EU3PesquisarSeriePorGenero,TestE2EU4EditarPerfil,TestE2ERegisterAndLogin,TestE2ESecurityRoles" "-DexcludedGroups=defeito-conhecido"
mvn test "-Dtest=TestE2EU4EditarPerfil" "-Dgroups=defeito-conhecido"
```
