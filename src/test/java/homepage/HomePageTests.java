package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests {

	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}

	@Test
	public void testValidarCarrinhoZerado_zeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
	}

	ProdutoPage produtoPage;

	@Test
	public void testValidarDetalhesdoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		produtoPage = homePage.clicarProduto(indice);

		String nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
	}

	LoginPage loginPage;

	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		// Clicar no botão Sign In na home page
		loginPage = homePage.clicarBotaoSignIn();

		// Preencher usuário e senha
		loginPage.preencherEmail("marcelo@teste.com");
		loginPage.preencherNome("marcelo");

		// Clicar no botão Sign In para logar
		loginPage.clicarBotaoSignIn();

		// Validar se o usuário está logado de fato
		assertThat(homePage.estaLogado("Marcelo Bittencourt"), is(true));
		carregarPaginaInicial();
	}

	@Test
	public void incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {

		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// --Pré-condição
		// Usuário logado
		if (!homePage.estaLogado("Marcelo Bittencourt")) {
			testLoginComSucesso_UsuarioLogado();
		}

		// --Teste
		// Selecionando produto
		testValidarDetalhesdoProduto_DescricaoEValorIguais();

		// Selecionar Tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista:" + listaOpcoes.size());

		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);

		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista:" + listaOpcoes.size());

		// Selecionar Cor
		produtoPage.selecionarCorPreta();

		// Selecionar Quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Adicionar no Carrinho de compras
		ModalProdutoPage modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));

		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
	}

}
