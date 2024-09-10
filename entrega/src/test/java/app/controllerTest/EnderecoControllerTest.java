package app.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import app.controller.EnderecoController;
import app.entity.Cliente;
import app.entity.Endereco;
import app.entity.Produto;
import app.repository.EnderecoRepository;

@SpringBootTest
public class EnderecoControllerTest {

	@Autowired
	EnderecoController enderecoController;
	
	@MockBean
	EnderecoRepository enderecoRepository;
	
	
	@BeforeEach
	void setup() {
		
		Endereco endereco = new Endereco();
		
		endereco.setId(1l);
		endereco.setRua("Catatau");
		endereco.setBairro("Ursinho");
		endereco.setCidade("Panda");
		endereco.setNum(12);
		endereco.setCep("85855-383");
		
		Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);
		
	}
	
	//####################### SAVE    OK / BAD  ########################
	
	@Test
	@DisplayName("Teste Unitário  ENDEREÇO # RESPONSE: 'Endereço salvo com sucesso' STATUS: OK")
	void cenarioEnderecoSalvar() {
		Endereco endereco = new Endereco();
		
		endereco.setId(1l);
		endereco.setRua("Catatau");
		endereco.setBairro("Ursinho");
		endereco.setCidade("Panda");
		endereco.setNum(12);
		endereco.setCep("85855-383");
		
		ResponseEntity<String> retorno = enderecoController.save(endereco);
		
		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		assertEquals("Endereço salvo com sucesso!", retorno.getBody());
		assertNotNull(retorno.getBody()); // verificando se o retorno não foi nulo
	}
	
	@Test
	@DisplayName("Teste Unitário  ENDEREÇO # RESPONSE:  // STATUS: BAD_REQUEST")
	void cenarioEnderecoSalvarErro() {
		
		// TODO:  esta ignorando o CEP fora do padrao que nao deveria passar
		//cep fora do padrao
		Endereco enderecoErrado = new Endereco();
		enderecoErrado.setId(1l);
		enderecoErrado.setRua("Catatau");
		enderecoErrado.setBairro("Ursinho");
		enderecoErrado.setCidade("Panda");
		enderecoErrado.setNum(12);
		enderecoErrado.setCep("8503");// cep fora do padrao
		
	//	ResponseEntity<String> retorno = enderecoController.save(null);
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = enderecoController.save(enderecoErrado);
		});
		//daria 400 ##TODO: em termos de validation
	}
	
	// #################### UPDATE OK / BAD #############################
	//versão  OK
	
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE: STATUS: OK ao atualizar um endereco existente")
	void cenarioEnderecoAtualizar() {
		// criando endereco
		Endereco enderecoExistente = new Endereco();
		enderecoExistente.setRua("Catatau");
		enderecoExistente.setBairro("Ursinho");
		enderecoExistente.setCidade("Panda");
		enderecoExistente.setNum(12);
		enderecoExistente.setCep("85855-383");
	

		Mockito.when(enderecoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(enderecoExistente));
		Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoExistente);

		ResponseEntity<String> retorno = enderecoController.update(enderecoExistente, 1L);

		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		assertEquals("Endereco atualizado com sucesso!", retorno.getBody());
	}
	
	// versão com erro
	
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE:  STATUS: BAD_REQUEST")
	void cenarioEnderecoAtualizarErro() {
		//tentando atualizar com um endereço nulo
		//cep fora do padrao
		Endereco enderecoErrado = new Endereco();
		enderecoErrado.setId(1l);
		enderecoErrado.setRua("Catatau");
		enderecoErrado.setBairro("Ursinho");
		enderecoErrado.setCidade("Panda");
		enderecoErrado.setNum(12);
		enderecoErrado.setCep("8503");// cep fora do padrao
		
	//	ResponseEntity<String> retorno = enderecoController.save(null);
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = enderecoController.update(enderecoErrado,0);
		});
		//daria 400 ##TODO: em termos de validation

	}
	
	
	// ################################## FIND BY ID  OK / BAD ##############
	
	//VERSÃO OK
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE:  STATUS: OK")
	void cenarioEnderecoBuscarPorId() {
		// criando endereco
		Endereco enderecoExistente = new Endereco();
		enderecoExistente.setId(1L);
		enderecoExistente.setRua("Catatau");
		enderecoExistente.setBairro("Ursinho");
		enderecoExistente.setCidade("Panda");
		enderecoExistente.setNum(12);
		enderecoExistente.setCep("85855-383");
		
		Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoExistente));
		
		ResponseEntity<Endereco> retorno = enderecoController.findById(1L);
		
	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    assertNotNull(retorno.getBody());
	    assertEquals(1L, retorno.getBody().getId());
	}
	
	// versão bad request
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE: 'Algo deu errado!' STATUS: BAD_REQUEST")
	void cenarioEnderecoBuscarPorIdErro() {
		

		Mockito.when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());
		
		ResponseEntity<Endereco> retorno = enderecoController.findById(99L);
		
	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertNull(retorno.getBody()); //nao nulo
	  //  assertEquals(1L, retorno.getBody().getId()); //id sera zero
	}
	
	// ########################## FIND ALL OK ######################
	
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE:  STATUS: OK ao buscar todos os endereços")
	void cenarioEnderecoBuscarTodos() {
		List<Endereco> lista = new ArrayList<>();
		lista.add(new Endereco(1L,"Jobim","Tom","MPB",13,"85555-383"));
		lista.add(new Endereco(2L,"Josuke","Higashikata","Morioh",4,"35555-383"));
		
		Mockito.when(enderecoRepository.findAll()).thenReturn(lista);
		
		ResponseEntity<List<Endereco>> retorno = enderecoController.findAll();
		
	    assertEquals(HttpStatus.OK, retorno.getStatusCode()); //retorno ok
	    assertNotNull(retorno.getBody()); //nao nulo
	    assertEquals(2, retorno.getBody().size()); //tamanho deve ser 2
		
		
	}
	//VERSÃO COM LISTA VAZIA
	@Test
	@DisplayName("Integração ENDERECO FIND ALL # RESPONSE: 'Lista vazia!' STATUS: OK")
	void cenarioEnderecoBuscarTodosErro() {
		ResponseEntity<List<Endereco>> retorno = enderecoController.findAll();
		assertTrue(retorno.getBody().isEmpty());
	}
	
	// VERSÃO COM EXCEÇÃO DO CATCH
	@Test
	@DisplayName("Integração ENDEREÇO FIND ALL # RESPONSE: STATUS: BAD_REQUEST ao ocorrer uma exceção")
	void cenarioEnderecoBuscarTodosComExcecao() {
	    Mockito.when(enderecoRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar produtos"));

	
	    ResponseEntity<List<Endereco>> retorno = enderecoController.findAll();


	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertNull(retorno.getBody());
	}
	
	//##################### DELETE   OK / BAD ################
	
	@Test
	@DisplayName("Teste Unitário  ENDEREÇO # RESPONSE:   STATUS: OK")
	void cenarioEnderecoExcluir() {
		// criando endereco
		Endereco enderecoExistente = new Endereco();
		enderecoExistente.setRua("Catatau");
		enderecoExistente.setBairro("Ursinho");
		enderecoExistente.setCidade("Panda");
		enderecoExistente.setNum(12);
		enderecoExistente.setCep("85855-383");
		
		Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoExistente));
		
	    ResponseEntity<String> retorno = enderecoController.delete(1L);

	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    //testando o assertFalse pra entender como funciona
		 assertFalse(retorno.getBody().startsWith("Algo deu errado!"));
		 //nessa caso oq ele espera é o posto de dar errado.
	}
	
	// versao com erro
	
	@Test
	@DisplayName("Teste Unitário ENDEREÇO # RESPONSE:  STATUS: BAD_REQUEST")
	void cenarioEnderecoExcluirInexistente() {
		Mockito.when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());
		
		ResponseEntity<String> retorno = enderecoController.delete(99L);
		
	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertEquals("Endereço não encontrado!", retorno.getBody());
	}
}
