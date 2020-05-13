package br.com.bottinocode.cdpmirante.pessoas;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TELEFONE")
public class Telefone {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Column(name = "ID")
    private Long id;
	
	@NotNull
    @Size(min = 3, max = 3, message = "DDD deve conter 3 digitos")    
	@Getter
	@Column(name = "DDD")
    private String ddd;
	
	@NotNull
    @Size(min = 8, max = 10, message = "Número deve conter entre 8 e 10 dígitos")    
	@Getter
	@Column(name = "NUMERO")
	private String numero;
	
	@Getter
	@Column(name = "DATA_CADASTRO", columnDefinition = "DATE")
	private Date dataCadastro = new Date();
	
	@Getter
	@Column(name = "LOGIN_OPERADOR")
	private String login;
	
	@NotNull
	@Enumerated
	@Getter
	@Setter
	@Column(name = "TIPO")	
	private Tipo tipo;

	@Setter
	@Getter
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_PESSOA")
	private Pessoa pessoa;
	
	
	public enum Tipo {
		CELULAR, FIXO, COMERCIAL;
	}

}
