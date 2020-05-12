package br.com.bottinocode.cdpmirante.pessoas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PESSOA")
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Column(name = "ID")
    private Long id;
	
	@NotNull
    @Size(max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Não deve conter números")
	@Getter
	@Column(name = "NOME")
    private String nome;
	
	@NotNull    
	@Size(min = 11, max = 14, message = "CPF ou CNPJ inválido")    
	@Getter
	@Column(name = "DOCUMENTO")
    private String documento;
	
	@NotNull
	@Past(message = "Data de nascimento não pode estar no futuro")
	@Getter
	
	@Column(name = "DATA_NASCIMENTO", columnDefinition = "DATE")	
	private Date dataNascimento = new Date();
	
	@NotNull
    @Size(max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Não deve conter números")
	@Getter
	@Column(name = "NOME_MAE")
    private String nomeMae;
	
	@NotNull
    @Size(max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Não deve conter números")
	@Getter
	@Column(name = "NOME_PAI")
    private String nomePai;
	
	@Getter
	@Column(name = "DATA_CADASTRO", columnDefinition = "DATE")	
	private Date dataCadastro = new Date();
	
	@NotNull
	@Getter
	@Column(name = "LOGIN_OPERADOR")
	private String login;
	
	@NotNull
	@Enumerated
	@Getter
	@Setter
	@Column(name = "TIPO")	
	private Tipo tipo;
	

}
