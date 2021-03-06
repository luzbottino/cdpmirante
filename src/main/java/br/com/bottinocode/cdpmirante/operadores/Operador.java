	package br.com.bottinocode.cdpmirante.operadores;

	import java.util.Date;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Enumerated;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.Table;
	import javax.validation.constraints.NotNull;
	import javax.validation.constraints.Pattern;
	import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OPERADOR")
public class Operador {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Column(name = "ID")
    private Long id;
	
	@NotNull
    @Size(max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Não deve conter números")
	@Setter
	@Getter
	@Column(name = "NOME")
    private String nome;	
	
	@NotNull
	@Size(max = 15)
	@Pattern(regexp = "[^0-9]*", message = "Apenas letras e os caracteres (-) e (_) são permitidos")
	@Setter
	@Getter
	@Column(name = "LOGIN")
	private String login;
	
	@NotNull
	@Size(min = 6, max = 15)
	@Pattern(regexp = "[^\\s]*", message = "Espaços em branco não são permitidos")
	@Setter
	@Getter
	@Column(name = "SENHA")
	private String senha;
	
	@Enumerated
	@Setter
	@Getter
	@Column(name = "PERFIL")	
	private Perfil perfil;
	
	@Getter
	@Column(name = "ADMINISTRADOR")
	private Boolean administrador = false;
		
	@Column(name = "DATA_CADASTRO", columnDefinition = "DATE")	
	private Date dataCadastro = new Date();	
	
	public enum Perfil {
		ANALISTA, GERENTE;
	}

}
