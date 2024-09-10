package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.entity.Endereco;
import app.repository.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public String save(Endereco endereco) {
		this.enderecoRepository.save(endereco);
		return "Endereço salvo com sucesso!";
	}
	/*
	public String saveMultiple(List<Endereco> enderecos) {
		enderecoRepository.saveAll(enderecos);
	    return "Endereços salvos com sucesso!";
	}*/
	public String update(Endereco endereco, long id) {
		endereco.setId(id);
		this.enderecoRepository.save(endereco);
		return "Endereco atualizado com sucesso!";
	}
	
	public Endereco findById(long id) {
		Optional<Endereco> optional = this.enderecoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public List<Endereco> findAll(){
		return this.enderecoRepository.findAll();
	}
	
	public String delete(long id) {
		this.enderecoRepository.deleteById(id);
		return "Endereco deletado com sucesso";
	}
	
}
