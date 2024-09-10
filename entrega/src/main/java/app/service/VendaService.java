package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.entity.Endereco;
import app.entity.Produto;
import app.entity.Venda;
import app.repository.ProdutoRepository;
import app.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {
	@Autowired
	private VendaRepository vendaRepositoy;
	
	//
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional 
	public String save(Venda venda) {
		//chama o método que calcula o valor total da venda com base nos produtos
		double valorTotal = this.calcularTotalVenda(venda.getProdutos());

		venda.setValorTotal(valorTotal);
		
		//chama o método que verifica a idade do cliente
        this.verificarIdadeCliente(venda.getCliente(), valorTotal);
      
		this.vendaRepositoy.save(venda);
		return "Venda salva com sucesso!"; 
	}
	/*
	//salvar multiplas vendas
		public String saveMultiple(List<Venda> vendas) {
	    this.vendaRepositoy.saveAll(vendas);
	    return "Vendas salvas com sucesso!";
	}
	*/
	private double calcularTotalVenda(List<Produto>produtos) {
		double valorTotal = 0;
		for(Produto produto : produtos) {
			
			Produto produtoAUX = produtoRepository.findById(produto.getId()).get();
			
			valorTotal+=produtoAUX.getPreco();
		}
		return valorTotal;
	}
	
    private void verificarIdadeCliente(Cliente cliente, double valorTotal) {
        if (cliente.getIdade() < 18 && valorTotal > 500) {
            throw new IllegalArgumentException("O valor total da venda não pode exceder R$500,00 para clientes menores de 18 anos.");
        }
    }
    
    public String update(Venda venda, long id) {
    	/*   if (!vendaRepositoy.existsById(id)) {
    	        throw new IllegalArgumentException("Venda não encontrada");
    	    }*/
        venda.setId(id);
		double valorTotal = this.calcularTotalVenda(venda.getProdutos());
		venda.setValorTotal(valorTotal);
        this.vendaRepositoy.save(venda);
        return "Venda atualizada com sucesso!";
    }
    
    public Venda findById(long id) {
    	Optional<Venda> optional = this.vendaRepositoy.findById(id);
    	//return optional.orElse(null); //mesmo que if else
    	return optional.get();
    }
    
    public List<Venda> findAll(){
    	return this.vendaRepositoy.findAll();
    }
    
    public String delete(long id) {
    	this.vendaRepositoy.deleteById(id);
    	return "Venda deletada com sucesso!";
    }
    
    //seleções
    public List<Venda> buscarVendasPorNomeCliente(String nomeCliente) {
    	return this.vendaRepositoy.findByClienteNomeContaining(nomeCliente);
    }

    public List<Venda> buscarVendasPorNomeFuncionario(String nomeFuncionario) {
        return this.vendaRepositoy.findByFuncionarioNomeContaining(nomeFuncionario);
    }

    public List<Venda> buscarTop10VendasPorValorTotal() {
        return this.vendaRepositoy.findTop10ByOrderByValorTotalDesc();
    }
}
