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

import app.controller.FuncionarioController;
import app.entity.Cliente;
//import app.entity.Cliente;
import app.entity.Endereco;
import app.entity.Funcionario;
import app.repository.FuncionarioRepository;

@SpringBootTest
public class FuncionarioControllerTest {

	@Autowired
	FuncionarioController funcionarioController;
	
	@MockBean
	FuncionarioRepository funcionarioRepository;
	
	Funcionario funcionario = new Funcionario();
	Endereco endereco = new Endereco();
	
	@BeforeEach
	void setup() {
		funcionario.setId(1L);
		funcionario.setNome("Roronoa Zoro");
		funcionario.setEmail("zorao@gmail.com");
		funcionario.setIdade(21);
		funcionario.setCpf("106.665.650-90");//valido
		funcionario.setFuncao("Caixa");
		
		endereco.setId(1L);
		endereco.setRua("Shimotsuki");
		endereco.setBairro("Wano");
		endereco.setCidade("Wado");
		endereco.setNum(13);
		endereco.setCep("85855-383");
		
		funcionario.setEndereco(endereco);
		
		Mockito.when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(funcionario);
		
	}
	
	// ################################# SAVE  OK / BAD #############################
	
	//  versão 200
	@Test
	@DisplayName("Integração FUNCIONARIO # RESPONSE: 'Funcionário salvo com sucesso!' STATUS: OK")
	void cenarioFuncionarioSalvar() {
		
		ResponseEntity<String> retorno = funcionarioController.save(funcionario);
		
		assertEquals(HttpStatus.OK,retorno.getStatusCode());
		assertEquals("Funcionario foi cadastrado com sucesso!",retorno.getBody());
		//na teoria é pra dar 200
	}
	//versão BAD_REQUEST
	// TODO: tive que alterar a controller porque nao os valores estavam passando nulos mesmo com annonations e @Valid ou sem ele
	@Test
	@DisplayName("Integração FUNCIONARIO # RESPONSE: 'Erro!' STATUS: Exception")
	void cenarioFuncionarioSalvarErro() {
		Funcionario funcionarioInvalido = new Funcionario();
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = funcionarioController.save(funcionarioInvalido);
		});
		//daria 400 ##TODO: em termos de validation
	}
	
	// ####################### FIND ALL OK ##############################
	
	//versão OK
	@Test
	@DisplayName("Integração FUNCIONARIO # RESPONSE 'Sucesso!' STATUS: OK")
	void cenarioFuncionarioBuscarTodos() {
		List<Funcionario> lista = new ArrayList<>();
		lista.add(new Funcionario(2L,"Carlos Silva","carlos@gmail.com",45,"113.919.999-40","Vendedor", new Endereco(3L, "Jobim", "Tom", "MPB", 13, "85555-383"), null));
		lista.add(new Funcionario(3L,"Julia Silva","julias@gmail.com",34,"829-444-749-87","Caixa", new Endereco(2L, "Jobim", "Tom", "MPB", 13, "85555-383"), null));
	
		Mockito.when(funcionarioRepository.findAll()).thenReturn(lista);
		ResponseEntity<List<Funcionario>> retorno = funcionarioController.findAll();
		
	    assertEquals(HttpStatus.OK, retorno.getStatusCode()); //retorno ok
	    assertNotNull(retorno.getBody()); //nao nulo
	    assertEquals(2, retorno.getBody().size()); //tamanho deve ser 2
	}
	// versão OK porém com lista vazia
	@Test
	@DisplayName("Integração Funcionario FIND ALL # RESPONSE: Lista vazia!  STATUS: BAD_REQUEST")
	void cenarioFuncionarioBuscarTodosErro() {
		//versão com nenhum funcionario
		
		ResponseEntity<List<Funcionario>>retorno = funcionarioController.findAll();
		
		assertTrue(retorno.getBody().isEmpty());//verifico se ta vazio
	}
	
	//##################### DELETE OK / BAD ############################
	
	//versão OK
	@Test
	@DisplayName("Integração FUNCIONARIO # RESPONSE: 'Sucesso!' STATUS: OK")
	void cenarioFuncionarioExcluir() {
		Mockito.when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
		ResponseEntity<String>retorno = funcionarioController.delete(1L);
	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    //testando o assertFalse pra entender como funciona
		 assertFalse(retorno.getBody().startsWith("Algo deu errado!"));
		 //nessa caso oq ele espera é o posto de dar errado.
	}
	
	// versão BAD_REQUEST
	//TODO: tive que alterar a controller porque a validação das annonations n funciona
	@Test
	@DisplayName("Integração FUNCIONARIO # RESPONSE: 'Falha!' STATUS: BAD_REQUEST")
	void cenarioFuncionarioExcluirErro() {
		Mockito.when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());
		
		ResponseEntity<String> retorno = funcionarioController.delete(99L);
	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertFalse(retorno.getBody().startsWith("Algo deu errado!"));
	}
	
	// ############################### UPDATE OK / BAD #########################
	
	//versão OK
	
	@Test
	@DisplayName("Integração FUNCIONARIO UPDATE # RESPONSE: 'Sucesso!'  STATUS: OK")
	void cenarioFuncionarioUpdate() {
		funcionario.setId(1L);
		funcionario.setNome("Vinsmoke Sanji");
		funcionario.setEmail("vinsanji@gmail.com");
		funcionario.setIdade(21);
		funcionario.setCpf("106.665.650-90");//valido
		funcionario.setFuncao("Caixa");
		
		endereco.setId(1L);
		endereco.setRua("Germa");
		endereco.setBairro("Double 6");
		endereco.setCidade("Whole Cake");
		endereco.setNum(13);
		endereco.setCep("85855-383");
		
		funcionario.setEndereco(endereco);
		
		Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario));
		Mockito.when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(funcionario);
		
		ResponseEntity<String> retorno = funcionarioController.update(funcionario, 1L);
		
		assertEquals(HttpStatus.OK,retorno.getStatusCode());
		assertEquals("Funcionario atualizado com sucesso!", retorno.getBody());
	}
	
	// versão bad request
	
	@Test
	@DisplayName("Integração  FUNCIONARIO UPDATE # RESPONSE: 'Erro!' STATUS: BAD_REQUEST")
	void cenarioFuncionarioUpdateErro() {
		//tentar atualizar com dados nulos
	
		
		;
		Funcionario funcionarioInvalido = new Funcionario();
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = funcionarioController.update(funcionarioInvalido,0);
		});
		//daria 400 ##TODO: em termos de validation
	}
	
	// ##################################### FIND BY ID  OK / BAD ################
	
	//versão OK
	
	@Test
	@DisplayName("Integração  FUNCIONARIO FIND BY ID  # RESPONSE: 'Sucesso!'  STATUS: OK")
	void cenarioFuncionarioBuscarPeloId() {
		
		Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario)); //global
		ResponseEntity<Funcionario> retorno = funcionarioController.findById(1L);
		
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(funcionario, retorno.getBody());
		
	}
	
	// versão BAD_REQUEST
	
	@Test
	@DisplayName("Integração  FUNCIONARIO  FIND BY ID # RESPONSE: 'Erro!' STATUS: BAD_REQUEST")
	void cenarioFuncionarioBuscarPeloIdErro() {
		
		Mockito.when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());
		ResponseEntity<Funcionario> retorno = funcionarioController.findById(99L);
		
		assertEquals(HttpStatus.BAD_REQUEST,retorno.getStatusCode());
		assertNull(retorno.getBody());
	}
	
}
