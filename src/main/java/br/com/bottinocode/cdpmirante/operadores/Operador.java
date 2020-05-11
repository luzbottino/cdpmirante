package br.com.bottinocode.cdpmirante.operadores;

import java.time.LocalDate;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OPERADOR")
public class Operador {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;
	
	@NotNull
    @Size(max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Não deve conter números")
	@Getter
	@Column(name = "NOME")
    private String nome;	
	
	@NotNull
	@Size(max = 15)
	@Pattern(regexp = "[^0-9]*", message = "Apenas letras e os caracteres (-) e (_) são permitidos")
	@Getter
	@Column(name = "LOGIN")
	private String login;
	
	@NotNull
	@Size(min = 6, max = 15)
	@Pattern(regexp = "[^\\s]*", message = "Espaços em branco não são permitidos")
	@Setter
	@Column(name = "SENHA")
	private String senha;
	
	@Enumerated
	@Getter
	@Column(name = "PERFIL")	
	private Perfil perfil;

	@Column(name = "ADMINISTRADOR")
	private Boolean administrador = false;

	@Getter	
	@Column(name = "DATA_CADASTRO", columnDefinition = "DATE")	
	private LocalDate dataCadastro = LocalDate.now();	

}
