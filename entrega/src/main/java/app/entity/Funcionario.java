package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "O nome é obrigatório!")
	private String nome;
	
	@Email(message = "Opa! Algo deu errado! O e-mail precisa ser válido!")
	private String email;
	
	@Min(value = 0, message = "Opa! Algo deu errado! A idade não pode ser negativa.")
	private int idade;
	
	@CPF(message = "Opa! Algo deu errado! CPF inválido")
	private String cpf;
	
	private String funcao;
	
	//@OneToOne
	//@JsonIgnoreProperties("funcionario")
	@OneToOne(cascade = CascadeType.ALL) //salva ambas entidades no banco
	// @NotNull(message = "Opa! Algo deu errado! O endereço é obrigatório.")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "funcionario")
	//@JsonIgnoreProperties("funcionario")
	@JsonIgnore
	private List<Venda> vendas;
}
