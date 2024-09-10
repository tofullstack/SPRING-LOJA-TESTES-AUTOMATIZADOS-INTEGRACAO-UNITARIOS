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

import app.entity.Endereco;
import app.service.EnderecoService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping("/save") // TESTE COLOCAR VALIDA PARA TESTAR A CONTROLLER
	public ResponseEntity<String> save(@Valid @RequestBody Endereco endereco){
		try {
			/*if(endereco == null) {
			 return new ResponseEntity<>("Algo deu errado! Endereço inválido", HttpStatus.BAD_REQUEST);	
			}*/
			
			String mensagem = this.enderecoService.save(endereco);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	/*
	@PostMapping("/saveMultiple")
	public ResponseEntity<String> saveMultiple(@RequestBody List<Endereco> enderecos) {
	    try {
	        String mensagem = this.enderecoService.saveMultiple(enderecos);
	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody Endereco endereco, @PathVariable long id){
		try {
			String mensagem = this.enderecoService.update(endereco, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado!"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
    @GetMapping("/findById/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable long id) {
        try {
            Endereco endereco = this.enderecoService.findById(id);
            if(endereco==null) {
            	 return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
            }
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<List<Endereco>> findAll() {
        try {
            List<Endereco> lista = this.enderecoService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
         //   String mensagem = this.enderecoService.delete(id);
        	Endereco endereco = this.enderecoService.findById(id);
        	if(endereco==null) {
        		return new ResponseEntity<>("Endereço não encontrado!",HttpStatus.BAD_REQUEST);
        	}
        	String mensagem = this.enderecoService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
