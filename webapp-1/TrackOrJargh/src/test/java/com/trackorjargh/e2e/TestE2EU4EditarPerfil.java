package com.trackorjargh.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Automacao do caso de teste manual U4 - Editar dados de perfil e gerenciar listas.
 *
 * Cada passo do caso manual vira um teste pequeno. Os passos que PASSARAM no teste
 * manual (1, 2, 4, 5, 7, 8, 11) formam testes que devem passar. Os passos que
 * FALHARAM no teste manual (3, 6 e 9) verificam o comportamento ESPERADO e, por isso,
 * falham enquanto o defeito existir. Eles levam a tag "defeito-conhecido" e ficam
 * fora da execucao padrao (ver README):
 *
 *   mvn test -Dtest=TestE2EU4EditarPerfil                       (testes que devem passar)
 *   mvn test -Dgroups=defeito-conhecido -DexcludedGroups=nenhuma (reproduz os defeitos)
 */
public class TestE2EU4EditarPerfil extends TestE2EBase {

    private static final By EMAIL = By.id("email");

    // ---------------------------------------------------------------- apoio

    private void abrirPerfilComoOscar() {
        goToPage("login");
        loginUser("oscar", "1234");
        driver.findElement(By.cssSelector("ul.nav-menu a[href='/miperfil']")).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")), "Perfil nao carregado", 5);
    }

    private void salvarPerfil() {
        clickJs(driver.findElement(By.id("acceptBtn")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                "Perfil nao recarregou apos salvar", 5);
    }

    private void trocarEmail(String novoEmail) {
        WebElement email = driver.findElement(EMAIL);
        email.clear();
        email.sendKeys(novoEmail);
    }

    private void restaurarEmail(String emailOriginal) {
        try {
            goToPage("miperfil");
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                    "Perfil nao carregado", 5);
            trocarEmail(emailOriginal);
            salvarPerfil();
        } catch (RuntimeException e) {
            log.warn("Nao foi possivel restaurar o email original: {}", emailOriginal);
        }
    }

    private void abrirAbaMisListas() {
        driver.findElement(By.id("v-pills-MyLists-tab")).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("MyLists")), "Aba Mis Listas nao exibida", 5);
    }

    private void criarLista(String nomeLista) {
        abrirAbaMisListas();
        driver.findElement(By.cssSelector("[data-target='#showAddList']")).click();
        ((JavascriptExecutor) driver).executeScript("document.getElementById('showAddList').classList.add('show');");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#showAddList input[name='listName']")),
                "Formulario de lista nao exibido", 5);
        driver.findElement(By.cssSelector("#showAddList input[name='listName']")).sendKeys(nomeLista);
        driver.findElement(By.name("list-submit")).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                "Perfil nao recarregou apos criar lista", 10);
        abrirAbaMisListas();
    }

    private void excluirLista(String nomeLista) {
        try {
            goToPage("miperfil");
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                    "Perfil nao carregado", 5);
            abrirAbaMisListas();
            clickJs(driver.findElement(By.cssSelector("button[name='deleteList'][value='" + nomeLista + "']")));
        } catch (RuntimeException e) {
            log.warn("Nao foi possivel excluir a lista de teste: {}", nomeLista);
        }
    }

    private boolean existeLista(String nomeLista) {
        for (WebElement lista : driver.findElements(By.id("CreatedLists"))) {
            if (textOf(lista).equals(nomeLista)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------- passos que PASSARAM no manual

    /** Passos 1 e 2: acessar o perfil e ver nome, email e avatar. */
    @Test
    public void perfilExibeNomeEmailEAvatar() {
        abrirPerfilComoOscar();

        assertThat(driver.getCurrentUrl()).endsWith("/miperfil");
        assertThat(driver.findElement(By.id("userName")).getAttribute("placeholder")).isEqualToIgnoringCase("oscar");
        assertThat(driver.findElement(EMAIL).getAttribute("value")).contains("@");
        assertThat(driver.findElement(By.id("userImage")).isDisplayed()).isTrue();
    }

    /** Passos 4 e 5: editar o email e salvar; a pagina devolvida mostra o novo email. */
    @Test
    public void emailEditadoApareceAposSalvar() {
        abrirPerfilComoOscar();
        String emailOriginal = driver.findElement(EMAIL).getAttribute("value");
        String novoEmail = "oscar_e2e_" + System.currentTimeMillis() + "@test.com";
        try {
            trocarEmail(novoEmail);
            assertThat(driver.findElement(EMAIL).getAttribute("value")).isEqualTo(novoEmail);

            salvarPerfil();

            assertThat(driver.findElement(EMAIL).getAttribute("value")).isEqualTo(novoEmail);
        } finally {
            restaurarEmail(emailOriginal);
        }
    }

    /** Passos 7, 8 e 11: abrir Mis Listas, criar uma lista e voltar ao perfil com a lista ainda la. */
    @Test
    public void listaCriadaApareceEPermaneceNoPerfil() {
        abrirPerfilComoOscar();
        String nomeLista = "e2e-lista-" + System.currentTimeMillis();
        try {
            criarLista(nomeLista);
            assertThat(existeLista(nomeLista)).isTrue();

            goToPage("miperfil");
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                    "Perfil nao carregado", 5);
            assertThat(driver.findElement(By.id("userName")).getAttribute("placeholder"))
                    .isEqualToIgnoringCase("oscar");
            abrirAbaMisListas();
            assertThat(existeLista(nomeLista)).isTrue();
        } finally {
            excluirLista(nomeLista);
        }
    }

    // ---------------------------------------------- passos que FALHARAM no manual

    /**
     * Passo 3 (issue #9): o campo "Nome" deveria aceitar uma nova entrada de texto.
     * No sistema atual o campo e somente leitura, entao este teste FALHA.
     */
    @Test
    @Tag("defeito-conhecido")
    public void nomeDoUsuarioDeveSerEditavel() {
        abrirPerfilComoOscar();

        WebElement nome = driver.findElement(By.id("userName"));
        nome.clear();
        nome.sendKeys("Novo Nome");

        assertThat(nome.getAttribute("value")).as("Campo Nome deve aceitar texto (issue #9)")
                .isEqualTo("Novo Nome");
    }

    /**
     * Passo 6 (issue #10): depois de salvar, o novo email deveria permanecer ao
     * recarregar a pagina (F5) e ao sair e voltar ao perfil. Falha se o email
     * antigo voltar; se este teste PASSAR, o defeito nao reproduz mais e o caso
     * deve ser reavaliado.
     */
    @Test
    @Tag("defeito-conhecido")
    public void emailEditadoDevePermanecerAposRecarregar() {
        abrirPerfilComoOscar();
        String emailOriginal = driver.findElement(EMAIL).getAttribute("value");
        String novoEmail = "oscar_e2e_" + System.currentTimeMillis() + "@test.com";
        try {
            trocarEmail(novoEmail);
            salvarPerfil();

            driver.navigate().refresh(); // F5 na pagina de resposta
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                    "Perfil nao recarregou", 5);
            assertThat(driver.findElement(EMAIL).getAttribute("value"))
                    .as("Email apos F5 (issue #10)").isEqualTo(novoEmail);

            goToPage("miperfil"); // sair e voltar ao perfil
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("v-pills-tab")),
                    "Perfil nao carregado", 5);
            assertThat(driver.findElement(EMAIL).getAttribute("value"))
                    .as("Email ao voltar ao perfil (issue #10)").isEqualTo(novoEmail);
        } finally {
            restaurarEmail(emailOriginal);
        }
    }

    /**
     * Passo 9 (issue #11): a lista criada deveria exibir botao/campo para adicionar
     * filmes, series ou livros. No sistema atual o card da lista so tem o botao de
     * excluir, entao este teste FALHA.
     */
    @Test
    @Tag("defeito-conhecido")
    public void listaDeveTerControleParaAdicionarItens() {
        abrirPerfilComoOscar();
        String nomeLista = "e2e-lista-" + System.currentTimeMillis();
        try {
            criarLista(nomeLista);
            assertThat(existeLista(nomeLista)).isTrue();

            List<WebElement> cards = driver.findElements(By.id("CreateList"));
            WebElement card = cards.get(cards.size() - 1);
            List<WebElement> controles = card.findElements(By.xpath(
                    ".//button[not(@name='deleteList') and not(@name='deleteContentURL')]"
                            + " | .//input | .//select"
                            + " | .//a[not(@id='CreatedLists') and not(@name='deleteContentName')]"));

            assertThat(controles).as("Controle para adicionar itens na lista (issue #11)").isNotEmpty();
        } finally {
            excluirLista(nomeLista);
        }
    }
}
