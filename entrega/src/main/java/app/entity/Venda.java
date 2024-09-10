package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//bate evolta
	@ManyToOne
	@JsonIgnoreProperties("vendas")
	@NotNull(message = "Opa! Algo deu errado! Um cliente deve estar vinculado à venda.") //garante que haja um objeto, notnull utilizado para objetos
	private Cliente cliente;
	
	//bate e volta
	@ManyToOne
	@JsonIgnoreProperties("vendas")
	private Funcionario funcionario;
	
	
	@ManyToMany
	@JoinTable(name="venda_tem_produto")
	 @NotEmpty(message = "Opa! A lista de produtos não pode estar vazia.") //garante que a LISTA nao esteja vazia, notempty
	private List<Produto> produtos;
	
	
	private double valorTotal;
	
	@NotBlank(message = "Opa! Algo deu errado! A observação é obrigatória.") //garante que nao seja vazio, notblank utilizado para string
	private String observacao;

}
