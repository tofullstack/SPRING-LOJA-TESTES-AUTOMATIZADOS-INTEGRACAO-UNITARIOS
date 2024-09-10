package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	private String rua;
	private String bairro;
	private String cidade;
	private int num;
	
	@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Opa! Algo deu errado! CEP não segue o padrão brasileiro (XXXXX-XXX).")
	private String cep;
}
