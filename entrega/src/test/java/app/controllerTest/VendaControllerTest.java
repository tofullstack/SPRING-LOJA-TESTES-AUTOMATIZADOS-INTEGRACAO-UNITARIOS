package app.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
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

import app.controller.VendaController;
import app.entity.Cliente;
import app.entity.Endereco;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Venda;
//import app.repository.ProdutoRepository;
import app.repository.VendaRepository;

@SpringBootTest //trazer o contexto do spring para o teste.
public class VendaControllerTest {

	@Autowired
	VendaController vendaController;
	
	@MockBean
	VendaRepository vendaRepository;
	
	/*@MockBean
	ProdutoRepository produtoRepository;*/
	
	//PERGUNTAR SE TEM PROBLEMA USAR VARIAVEL GLOBAL ##TODO
	Cliente cliente = new Cliente();
	Endereco endereco = new Endereco();
	Endereco endereco2= new Endereco();
	Funcionario funcionario = new Funcionario();
	Produto produto = new Produto();
	Venda venda = new Venda();
	
	//nao sei se devo mockar mais que apenas venda e prosuto
	
    @BeforeEach
    void setup() {
        // preparando 

    	
        endereco.setId(1L);
        endereco.setRua("Rua Exemplo");
        endereco.setBairro("Bairro Exemplo");
        endereco.setCidade("Cidade Exemplo");
        endereco.setNum(123);
        endereco.setCep("01040-000");
        
        endereco2.setId(2L);
        endereco2.setRua("Rua Exemplo");
        endereco2.setBairro("Bairro Exemplo");
        endereco2.setCidade("Cidade Exemplo");
        endereco2.setNum(123);
        endereco2.setCep("01040-290");



        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao.silva@gmail.com");
        cliente.setIdade(30);
        cliente.setCpf("095.837.830-48");
        cliente.setTelefone("(11) 98765-4321");
        cliente.setEndereco(endereco);


        funcionario.setId(1L);
        funcionario.setNome("Maria Santos");
        funcionario.setEmail("maria.santos@gmail.com");
        funcionario.setIdade(28);
        funcionario.setCpf("712.595.910-50");
        funcionario.setFuncao("Vendedora");
        funcionario.setEndereco(endereco2);


        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);

        // preparando venda mock do repo/save
        //Venda venda = new Venda();
        venda.setId(1L);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(Arrays.asList(produto));
        venda.setValorTotal(100.0);
        venda.setObservacao("Venda teste integração");

        Mockito.when(vendaRepository.save(Mockito.any(Venda.class))).thenReturn(venda);
        
       // Mockito.when(vendaRepository.findAll()).thenReturn(Arrays.asList());
    }

// ############################### SAVE  OK/BAD ##################################################
  // 1
    @Test
    @DisplayName("Integração VENDA # RESPONSE: 'Venda salva com sucesso!' STATUS: OK")
    void cenarioVendaSalvar() {
 
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(Arrays.asList(produto));
        venda.setObservacao("Venda de teste");


        ResponseEntity<String> response = vendaController.save(venda);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda salva com sucesso!", response.getBody());
    }
    
    // 2   versão que da errado
  
   /* @Test
    @DisplayName("Integração VENDA # RESPONSE: 'Erro de validação' STATUS: BAD REQUEST")
    void cenarioVendaSalvarErroValidacao() {
   
        Venda vendaInvalida = new Venda();
 
        Exception excecao = assertThrows(Exception.class, () -> {
            vendaController.save(vendaInvalida);
        });

      
        String mensagemEsperada = "Erro de validação";  
        String mensagem = excecao.getMessage();

        assertTrue(mensagem.contains(mensagemEsperada));
    }*/

	@Test
	@DisplayName("Teste Unitário PRODUTO # RESPONSE: STATUS: BAD_REQUEST # Produto com nome nulo.")
	void cenarioVendaSalvarNula() {
		

		Venda vendaInvalida = new Venda();
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = vendaController.save(vendaInvalida);
		});
		//daria 400 ##TODO: em termos de validation
	}
    
    
    // ########################## FIND ALL  OK/BAD ####################################
    // 1
    @Test
    @DisplayName("Integração FIND ALL # RESPONSE: Lista de vendas encontrada STATUS: OK")
    void cenarioFindAllSucesso() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(Arrays.asList(produto));
        venda.setObservacao("Venda encontrada");

        List<Venda> vendas = Arrays.asList(venda);

       Mockito.when(vendaRepository.findAll()).thenReturn(vendas);

        ResponseEntity<List<Venda>> response = vendaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendas, response.getBody());
    }

    // 2  versao que da errado
    
    @Test
    @DisplayName("Integração FIND ALL # RESPONSE: Nenhuma venda encontrada STATUS: BAD REQUEST")
    void cenarioFindAllNenhumaVenda() {
       

        ResponseEntity<List<Venda>> response = vendaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    
    // ##
    // #########################  UPDATE OK/BAD #########################################
    
    // 1 
    
    @Test
    @DisplayName("Integração UPDATE # RESPONSE: 'Venda atualizada com sucesso!' STATUS: OK")
    void cenarioUpdateVendaSucesso() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(Arrays.asList(produto));
        venda.setObservacao("Venda atualizada");

        // TODO: perguntar ao professor se o Optional se usa dessa maneira
        Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(venda));
        Mockito.when(vendaRepository.save(Mockito.any(Venda.class))).thenReturn(venda);

        ResponseEntity<String> response = vendaController.update(venda, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda atualizada com sucesso!", response.getBody());
    }
    
    // 2 - versão que da erro
    
    @Test
    @DisplayName("Integração UPDATE ERRO # RESPONSE: 'Exception' STATUS: Exception")
    void cenarioUpdateVendaErro() {
    	
	 Venda vendaInvalida = new Venda();
		
		assertThrows(Exception.class, ()->{
			ResponseEntity<String> retorno = vendaController.update(vendaInvalida,0);
		});
		//daria 400 ##TODO: em termos de validation
    }

    //#################################### FIND BY ID OK/BAD ################################
    
    // 1
    @Test
    @DisplayName("Integração FIND BY ID # RESPONSE: 'Venda encontrada' STATUS: OK")
    void cenarioFindByIdSucesso() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(Arrays.asList(produto));
        venda.setObservacao("Venda encontrada");

        Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(venda));

        ResponseEntity<Venda> response = vendaController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
    }

    // 2  TODO: lembrete -> tive que alterar o método findById na controller lembrar de fazer o mesmo no JUNO
    @Test
    @DisplayName("Integração FIND BY ID # RESPONSE: 'Venda não encontrada' STATUS: BAD REQUEST")
    void cenarioFindByIdNaoEncontrada() {
        // simula venda nao encontrada
        Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Venda> response = vendaController.findById(1L);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    @Test
    @DisplayName("Integração FIND BY ID # RESPONSE: 'Venda não encontrada' STATUS: BAD REQUEST")
    void cenarioFindByIdNegativo() {
        // simula venda nao encontrada
        Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Venda> response = vendaController.findById(-1);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    
    // ########################## DELETAR OK / BAD ######################################
    // 1
    @Test
    @DisplayName("Integração DELETE # RESPONSE: 'Venda excluída com sucesso!' STATUS: OK")
    void cenarioDeleteSucesso() {
        // Simula uma resposta sem comportamento específico
        ResponseEntity<String> response = vendaController.delete(1L);

        // Verifica se a resposta está correta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda deletada com sucesso!", response.getBody());
    }
 
    // 2
    @Test
    @DisplayName("Integração DELETE # RESPONSE: 'Erro ao excluir venda' STATUS: BAD REQUEST")
    void cenarioDeleteErro() {
        // Simula um erro ao tentar excluir a venda no repositório
        doThrow(new RuntimeException("Erro ao excluir venda")).when(vendaRepository).deleteById(anyLong());

        ResponseEntity<String> response = vendaController.delete(1L);

        // Verifica se a resposta está correta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody()); // Verifica que o corpo não é nulo
        assertEquals("Algo deu errado ao excluir a venda!", response.getBody()); // Verifica que a mensagem de erro está correta
    }
    /// ############################### TESTES DE FILTROS ####################################################
    
    // teste para buscar vendas pelo nome do cliente
    @Test
    @DisplayName("Buscar vendas por nome do cliente")
    void cenarioBuscarVendasPorNomeCliente() {
        List<Venda> vendas = Arrays.asList(venda);
        Mockito.when(vendaRepository.findByClienteNomeContaining("João Silva")).thenReturn(vendas);

        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeCliente("João Silva");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendas, response.getBody());
    }
    
    // teste para buscar vendas pelo nome do funcionario
    @Test
    @DisplayName("Buscar vendas por nome do funcionário")
    void cenarioBuscarVendasPorNomeFuncionario() {
        List<Venda> vendas = Arrays.asList(venda);
        Mockito.when(vendaRepository.findByFuncionarioNomeContaining("Maria Santos")).thenReturn(vendas);

        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeFuncionario("Maria Santos");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendas, response.getBody());
    }
    
    //teste para buscar as top vendas
    @Test
    @DisplayName("Buscar as top 10 vendas por valor total")
    void cenarioBuscarTop10VendasPorValorTotal() {
        List<Venda> vendas = Arrays.asList(venda);
        Mockito.when(vendaRepository.findTop10ByOrderByValorTotalDesc()).thenReturn(vendas);

        ResponseEntity<List<Venda>> response = vendaController.buscarTop10VendasPorValorTotal();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() <= 10); // perguntar se ok usar isso 
    }
}
