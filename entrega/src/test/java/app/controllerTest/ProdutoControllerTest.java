package app.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.ProdutoController;
import app.entity.Cliente;
import app.entity.Produto;
import app.repository.ProdutoRepository;

@SpringBootTest
public class ProdutoControllerTest {

	@Autowired
	ProdutoController produtoController;

	@MockBean
	ProdutoRepository produtoRepository;

	Produto produto;

	// preparando o setup
	@BeforeEach
	void setup() {

		Produto produto = new Produto();

		// preparando um produto

		produto.setId(1l);
		produto.setNome("Chuteira");
		produto.setDescricao("Esportivos");
		produto.setPreco(500.00);

		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

	}

	// ############################### SAVE OK / BAD #####################

	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: 'Produto salvo com sucesso!'   STATUS: OK")
	void cenarioProdutoSalvar() {
		Produto produtoParaSalvar = new Produto();
		produtoParaSalvar.setId(1L);
		produtoParaSalvar.setNome("Chuteira");
		produtoParaSalvar.setDescricao("Esportivos");
		produtoParaSalvar.setPreco(500.00);

		ResponseEntity<String> retorno = produtoController.save(produtoParaSalvar);

		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		assertEquals("Produto salvo com sucesso!", retorno.getBody());
		assertNotNull(retorno.getBody()); // verificando se o retorno não foi nulo
	}

	// versão com erro
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: BAD_REQUEST # Produto com nome nulo.")
	void cenarioProdutoSalvarNomeNulo() {
		// nome nulo // NOME É OBRIGATORIO
		Produto produtoComNomeNulo = new Produto();
		produtoComNomeNulo.setId(2L);
		produtoComNomeNulo.setDescricao("Esportivos");
		produtoComNomeNulo.setPreco(400.00);

		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = produtoController.save(produtoComNomeNulo);
		});
		//daria 400 ##TODO: em termos de validation
	}

	// ########################### UPDATE OK / BAD ##################
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: OK ao atualizar um produto existente")
	void cenarioProdutoAtualizar() {
		// criando produto
		Produto produtoExistente = new Produto();
		produtoExistente.setId(1L);
		produtoExistente.setNome("Tênis");
		produtoExistente.setDescricao("Calçado esportivo");
		produtoExistente.setPreco(300.00);

		Mockito.when(produtoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(produtoExistente));
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produtoExistente);

		ResponseEntity<String> retorno = produtoController.update(produtoExistente, 1L);

		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		assertEquals("Produto atualizado com sucesso!", retorno.getBody());
	}

	// VERSÃO COM ERRO
	
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: BAD_REQUEST ao tentar atualizar com nome nulo")
	void cenarioProdutoAtualizarNomeNulo() {
		Produto produtoInvalido = new Produto();
		produtoInvalido.setId(2L);
		produtoInvalido.setDescricao("Novo Produto");
		produtoInvalido.setPreco(200.00);
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = produtoController.update(produtoInvalido,0);
		});
		//daria 400 ##TODO: em termos de validation
	}
	
	
	// ############################# FIND BY ID   OK / BAD  ###############
	
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: OK ao buscar por ID existente")
	void cenarioProdutoBuscarPorId() {
	    Produto produtoExistente = new Produto();
	    produtoExistente.setId(1L);
	    produtoExistente.setNome("Chuteira");
	    produtoExistente.setDescricao("Calçado esportivo");
	    produtoExistente.setPreco(500.00);

	    Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));

	    ResponseEntity<Produto> retorno = produtoController.findById(1L);

	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    assertNotNull(retorno.getBody());
	    assertEquals(1L, retorno.getBody().getId());
	}
	
	// versão com erro
	
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: BAD_REQUEST ao buscar por ID inexistente")
	void cenarioProdutoBuscarPorIdInexistente() {
	    Mockito.when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

	    ResponseEntity<Produto> retorno = produtoController.findById(99L);

	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertNull(retorno.getBody());
	}
	// TRATAMENTO DE EXCEÇÃO CATCH TODO
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: INTERNAL_SERVER_ERROR ao ocorrer uma exceção na busca por ID")
	void cenarioProdutoBuscarPorIdComExcecao() {

	    Mockito.when(produtoRepository.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar produto por ID"));


	    ResponseEntity<Produto> retorno = produtoController.findById(1L);

	    //teste de usar o internal server erro
	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertNull(retorno.getBody());
	}

	
	// ##################################### FINDALL ######################
	
	//versão OK
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: OK ao buscar todos os produtos")
	void cenarioProdutoBuscarTodos() {
	    List<Produto> listaProdutos = new ArrayList<>();
	    listaProdutos.add(new Produto(1L, "Chuteira", "Calçado esportivo", 500.00));
	    listaProdutos.add(new Produto(2L, "Camisa", "Vestuário", 150.00));

	    Mockito.when(produtoRepository.findAll()).thenReturn(listaProdutos);

	    ResponseEntity<List<Produto>> retorno = produtoController.findAll();

	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    assertNotNull(retorno.getBody());
	    assertEquals(2, retorno.getBody().size());
	}

	//versão OK porem vazio
	
	@Test
	@DisplayName("Integração PRODUTO FIND ALL # RESPONSE: 'Lista vazia!' STATUS: OK")
	void cenarioProdutoBuscarTodosErro() {
		ResponseEntity<List<Produto>> retorno = produtoController.findAll();
		assertTrue(retorno.getBody().isEmpty());
	}
	
	// versão para cobrir o CATCH TESTE TODO
	@Test
	@DisplayName("Integração PRODUTO FIND ALL # RESPONSE: STATUS: BAD_REQUEST ao ocorrer uma exceção")
	void cenarioProdutoBuscarTodosComExcecao() {
	    Mockito.when(produtoRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar produtos"));

	
	    ResponseEntity<List<Produto>> retorno = produtoController.findAll();


	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertNull(retorno.getBody());
	}
	
	// ################################### DELETE  OK / BAD ##################
	
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: OK ao excluir produto existente")
	void cenarioProdutoExcluir() {
	    Produto produtoExistente = new Produto();
	    produtoExistente.setId(1L);
	    produtoExistente.setNome("Chuteira");
	    produtoExistente.setDescricao("Calçado esportivo");
	    produtoExistente.setPreco(500.00);

	    Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
	    Mockito.doNothing().when(produtoRepository).deleteById(1L);

	    ResponseEntity<String> retorno = produtoController.delete(1L);

	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    assertEquals("Produto deletado com sucesso!", retorno.getBody());
	}
	
	//versão com erro
	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: BAD_REQUEST ao tentar excluir produto inexistente")
	void cenarioProdutoExcluirInexistente() {
	    Mockito.when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

	    ResponseEntity<String> retorno = produtoController.delete(99L);

	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertEquals("Produto não encontrado!", retorno.getBody());
	}
}
