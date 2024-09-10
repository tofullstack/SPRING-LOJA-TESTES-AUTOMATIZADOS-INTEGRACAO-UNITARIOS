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

import app.controller.ClienteController;
import app.entity.Cliente;
import app.entity.Endereco;
import app.repository.ClienteRepository;

@SpringBootTest
public class ClienteControllerTest {

	@Autowired
	ClienteController clienteController;
	
	@MockBean
	ClienteRepository clienteRepository;
	
	Cliente cliente = new Cliente();
	Endereco endereco = new Endereco();
	
	
	@BeforeEach
	void setup() {
		
		cliente.setId(1L);
		cliente.setNome("Maria Julia");
		cliente.setEmail("mariajulia@gmail.com");
		cliente.setIdade(25);
		cliente.setCpf("106.665.650-90");//valido
		cliente.setTelefone("(11) 98765-4321"); //valido
		
		endereco.setId(1L);
		endereco.setRua("Catatau");
		endereco.setBairro("Ursinho");
		endereco.setCidade("Panda");
		endereco.setNum(12);
		endereco.setCep("85855-383");
		
		cliente.setEndereco(endereco);
		
		Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
		
	}
	
	// ##################### SAVE  OK / BAD ##################
	
	//  versão 200
	@Test
	@DisplayName("Integração CLIENTE # RESPONSE: 'Cliente salvo com sucesso!' STATUS: OK")
	void cenarioClienteSalvar() {
		
		ResponseEntity<String> retorno = clienteController.save(cliente);
		
		assertEquals(HttpStatus.OK,retorno.getStatusCode());
		assertEquals("Cliente foi cadastrado com sucesso!",retorno.getBody());
		//na teoria é pra dar 200
	}

	@Test
	@DisplayName("Integração CLIENTE # RESPONSE: 'Erro!' STATUS: Exception")
	void cenarioClienteSalvarErro() {
		Cliente clienteInvalido = new Cliente();
		clienteInvalido.setCpf("12");
		//cliente sem nada
		
	
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = clienteController.save(clienteInvalido);
		});
		//daria 400 ##TODO: em termos de validation

	}
	
	//teste bad
	@Test
	@DisplayName("Integração CLIENTE # RESPONSE: 'CPF inválido!' STATUS: BAD_REQUEST")
	void cenarioClienteSalvarCpfInvalido() {
	    Cliente clienteInvalido = new Cliente();
	    clienteInvalido.setCpf("123"); // CPF inválido 
	    cliente.setIdade(-4);


		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = clienteController.save(clienteInvalido);
		});
		//daria 400 ##TODO: em termos de validation
	}

	
	//########################## FINDALL OK ###########################
	
	@Test
	@DisplayName("Integração CLIENTE # RESPONSE 'Sucesso!' STATUS: OK")
	void cenarioClienteBuscarTodos() {
		List<Cliente> lista = new ArrayList<>();
        // lista de clientes
		lista.add(new Cliente(2L,"Carlos Silva","carlos@gmail.com",45,"113.919.999-40","(11) 98765-4321", new Endereco(1L, "Jobim", "Tom", "MPB", 13, "85555-383"), null));
		lista.add(new Cliente(3L,"Julia Silva","julias@gmail.com",34,"829-444-749-87","(11) 98765-4321", new Endereco(2L, "Jobim", "Tom", "MPB", 13, "85555-383"), null));
		
		Mockito.when(clienteRepository.findAll()).thenReturn(lista);
		ResponseEntity<List<Cliente>> retorno = clienteController.findAll();
		
	    assertEquals(HttpStatus.OK, retorno.getStatusCode()); //retorno ok
	    assertNotNull(retorno.getBody()); //nao nulo
	    assertEquals(2, retorno.getBody().size()); //tamanho deve ser 2

	}
	// lista vazia
	@Test
	@DisplayName("Integração CLIENTE FIND ALL # RESPONSE: Lista vazia!  STATUS: BAD_REQUEST")
	void cenarioClienteBuscarTodosErro() {
		//versão com nenhum cliente
		
		ResponseEntity<List<Cliente>>retorno = clienteController.findAll();
		
		assertTrue(retorno.getBody().isEmpty());//verifico se ta vazio
	}
	// ####################### DELETE OK / BAD REQUEST ###########
	
	//versão OK
	@Test
	@DisplayName("Integração CLIENTE # RESPONSE: 'Sucesso!' STATUS: OK")
	void cenarioClienteExcluir() {
		
		Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
		ResponseEntity<String> retorno = clienteController.delete(1L);
	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    //testando o assertFalse pra entender como funciona
		 assertFalse(retorno.getBody().startsWith("Algo deu errado!"));
		 //nessa caso oq ele espera é o posto de dar errado.
	}
	
	// versão BAD_REQUEST
	
	@Test
	@DisplayName("Integração CLIENTE # RESPONSE: 'Falha!' STATUS: BAD_REQUEST")
	void cenarioClienteExcluirErro() {
		Mockito.when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
		
		ResponseEntity<String> retorno = clienteController.delete(99L);
	    assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	    assertFalse(retorno.getBody().startsWith("Algo deu errado!"));
	}
	
	//######################### UPDATE  OK / BAD #########################
	
	//versão OK
	
	@Test
	@DisplayName("Integração CLIENTE UPDATE # RESPONSE: 'Sucesso!'  STATUS: OK")
	void cenarioClienteUpdate() {
		cliente.setId(1L);
		cliente.setNome("Nami Cat");
		cliente.setEmail("momnami@gmail.com");
		cliente.setIdade(25);
		cliente.setCpf("106.665.650-90");//valido
		cliente.setTelefone("(11) 98765-4321"); //valido
		
		endereco.setId(1L);
		endereco.setRua("Polar");
		endereco.setBairro("Pardo");
		endereco.setCidade("Panda");
		endereco.setNum(12);
		endereco.setCep("85855-383");
		
		cliente.setEndereco(endereco);
		
		Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
		
		ResponseEntity<String> retorno = clienteController.update(cliente, 1L);
		
		assertEquals(HttpStatus.OK,retorno.getStatusCode());
		assertEquals("Cliente atualizado com sucesso!", retorno.getBody());
	}
	
	// versão bad request
	
	@Test
	@DisplayName("Integração  CLIENTE UPDATE # RESPONSE: 'Erro!' STATUS: BAD_REQUEST")
	void cenarioClienteUpdateErro() {
		//tentar atualizar com dados nulos
		//ResponseEntity<String> retorno = clienteController.update(null, 0);
		Cliente clienteInvalido = new Cliente();
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = clienteController.update(clienteInvalido,0);
		});
		//daria 400 ##TODO: em termos de validation
	}
	
	// ############################# FIND BY ID  OK / BAD ####################
	
	//versão OK
	
	@Test
	@DisplayName("Integração  CLIENTE FIND BY ID  # RESPONSE: 'Sucesso!'  STATUS: OK")
	void cenarioClienteBuscarPeloId() {
		
		Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cliente)); //global
		ResponseEntity<Cliente> retorno = clienteController.findById(1L);
		
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(cliente, retorno.getBody());
		
	}
	
	// versão BAD_REQUEST
	
	@Test
	@DisplayName("Integração  CLIENTE  FIND BY ID # RESPONSE: 'Erro!' STATUS: BAD_REQUEST")
	void cenarioClienteBuscarPeloIdErro() {
		
		Mockito.when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
		ResponseEntity<Cliente> retorno = clienteController.findById(99L);
		
		assertEquals(HttpStatus.BAD_REQUEST,retorno.getStatusCode());
		assertNull(retorno.getBody());
	}
}
