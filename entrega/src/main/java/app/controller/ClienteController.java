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

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Cliente cliente){
		try {
          /*  if (cliente == null || cliente.getNome() == null || cliente.getNome().isEmpty()) {
                return new ResponseEntity<>("Algo deu errado!", HttpStatus.BAD_REQUEST);
            }*/
			String mensagem = this.clienteService.save(cliente);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
/*	//para salvar no JSON mais de um cliente de uma unica vez
	@PostMapping("/saveMultiple")
	public ResponseEntity<String> saveMultiple(@RequestBody List<Cliente> clientes) {
	    try {
	        String mensagem = this.clienteService.saveMultiple(clientes);
	        return new ResponseEntity<>(mensagem, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
*/

	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody Cliente cliente, @PathVariable long id){
		try {
			String mensagem = this.clienteService.update(cliente, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado!"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
    @GetMapping("/findById/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable long id) {
        try {
            Cliente cliente = this.clienteService.findById(id);
            if(cliente == null) {
            	return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<List<Cliente>> findAll() {
        try {
            List<Cliente> lista = this.clienteService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
         Cliente cliente = this.clienteService.findById(id);
         if(cliente==null) {
        	 return new ResponseEntity<>("Cliente não encontrado!",HttpStatus.BAD_REQUEST);
         }
        	String mensagem = this.clienteService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
