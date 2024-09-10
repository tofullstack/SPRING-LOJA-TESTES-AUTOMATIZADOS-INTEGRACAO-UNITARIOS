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

import app.entity.Produto;
import app.service.ProdutoService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("api/produto")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Produto produto) {
	    try {
	        // validaçao
	      /*  if (produto == null || produto.getNome() == null || produto.getNome().isBlank()) {
	            return new ResponseEntity<>("Algo deu errado! Produto inválido", HttpStatus.BAD_REQUEST);
	        }*/

	        String mensagem = this.produtoService.save(produto);
	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

	/*
	
 	@PostMapping("/saveMultiple")
	public ResponseEntity<String> saveMultiple(@RequestBody List<Produto> produtos) {
	    try {
	        String mensagem = this.produtoService.saveMultiple(produtos);
	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
*/
 	@PutMapping("/update/{id}")
 	public ResponseEntity<String> update(@Valid @RequestBody Produto produto, @PathVariable long id) {
 	    try {
 	        // Validação básica
 	      /*  if (produto == null || produto.getNome() == null || produto.getNome().isBlank()) {
 	            return new ResponseEntity<>("Algo deu errado! Produto inválido", HttpStatus.BAD_REQUEST);
 	        }*/
 	        
 	        String mensagem = this.produtoService.update(produto, id);
 	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
 	    } catch (Exception e) {
 	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
 	    }
 	}
	
 	@GetMapping("/findById/{id}")
 	public ResponseEntity<Produto> findById(@PathVariable long id) {
 	    try {
 	        Produto produto = this.produtoService.findById(id);
 	        if (produto == null) {
 	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retornar BAD_REQUEST se o produto não for encontrado
 	        }
 	        return new ResponseEntity<>(produto, HttpStatus.OK);
 	    } catch (Exception e) {
 	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
 	    }
 	}
    
    @GetMapping("/findAll")
    public ResponseEntity<List<Produto>> findAll() {
        try {
            List<Produto> lista = this.produtoService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            Produto produto = this.produtoService.findById(id);
            if (produto == null) {
                return new ResponseEntity<>("Produto não encontrado!", HttpStatus.BAD_REQUEST);
            }
            String mensagem = this.produtoService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
