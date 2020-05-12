package br.com.bottinocode.cdpmirante.util;

import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Stateless
public class Validador<T> {

	@Inject
	private Validator validator;

	public Map<String, String> validarObjeto(T elemento) {
		return validator.validate(elemento).stream()
				.collect(Collectors.toMap(Validador::getPropertyPath, ConstraintViolation::getMessage));
		
	}
	
	static String getPropertyPath(ConstraintViolation<?> constraintViolation) {
		return constraintViolation.getPropertyPath().toString();
	}

}
