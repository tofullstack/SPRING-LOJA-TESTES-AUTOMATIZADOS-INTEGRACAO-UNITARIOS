package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "O nome é obrigatório.")
    @Pattern(regexp = "^(?=.*\\s)(?=.*\\b\\w{2,}).*$", message = "Opa! Algo deu errado! O nome deve conter pelo menos duas palavras e um espaço.") //regex do google
	private String nome;
	
	@Email(message = "Opa! Algo deu errado! E-mail inválido!")
	private String email;
	
	@Min(value = 0, message = "Opa! Algo deu errado! A idade não pode ser negativa.")
	private int idade;
	
	@CPF(message = "Opa! Algo deu errado! CPF inválido")
	private String cpf;
	
	
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Opa! Algo deu errado! O telefone deve seguir o padrão (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
	private String telefone;
	
	
	
	//@OneToOne
	@OneToOne//salva ambas entidades no banco
	//@JsonIgnoreProperties("cliente")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "cliente")
	 //@JsonIgnoreProperties({"cliente", "produtos"})
	@JsonIgnore
	private List<Venda> vendas;
	
}
