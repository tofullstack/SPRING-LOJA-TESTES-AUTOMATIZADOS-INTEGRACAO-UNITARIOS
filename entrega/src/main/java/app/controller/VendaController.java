package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Venda;
import app.service.VendaService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("api/venda")
public class VendaController {
	@Autowired
	private VendaService vendaService;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Venda venda){
		try {
			String mensagem = this.vendaService.save(venda);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
/*
 	@PostMapping("/saveMultiple")
	public ResponseEntity<String> saveMultiple(@RequestBody List<Venda> vendas) {
	    try {
	        String mensagem = this.vendaService.saveMultiple(vendas);
	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
 	*/
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody Venda venda, @PathVariable long id){
		try {
			String mensagem = this.vendaService.update(venda, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado!"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Venda> findById(@PathVariable long id) {
	    try {
	        Venda venda = this.vendaService.findById(id);
	       
	        return new ResponseEntity<>(venda, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}
    
    @GetMapping("/findAll")
    public ResponseEntity<List<Venda>> findAll() {
        try {
            List<Venda> lista = this.vendaService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = vendaService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            // mensagem de erro padrão ao invés de null
            return new ResponseEntity<>("Algo deu errado ao excluir a venda!", HttpStatus.BAD_REQUEST);
        }
    }
    
    //seleções
    
    @GetMapping("/cliente/{nomeCliente}")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeCliente(@PathVariable String nomeCliente) {
        List<Venda> vendas = vendaService.buscarVendasPorNomeCliente(nomeCliente);
        return ResponseEntity.ok(vendas);  // Retornando com ResponseEntity
    }

    @GetMapping("/funcionario/{nomeFuncionario}")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeFuncionario(@PathVariable String nomeFuncionario) {
        List<Venda> vendas = vendaService.buscarVendasPorNomeFuncionario(nomeFuncionario);
        return ResponseEntity.ok(vendas);  // Retornando com ResponseEntity
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Venda>> buscarTop10VendasPorValorTotal() {
        List<Venda> vendas = vendaService.buscarTop10VendasPorValorTotal();
        return ResponseEntity.ok(vendas);  // Retornando com ResponseEntity
    }
}
