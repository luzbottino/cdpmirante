package br.com.bottinocode.cdpmirante.operadores;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class OperadoresService {
	
	@Inject
	private Logger log;
	
	@Inject
    private Event<Operador> event;
 
    @Inject
    private EntityManager em;
    
    public void criar(Operador operador) {
		log.info("Registrando " + operador.getNome());
		em.persist(operador);
		event.fire(operador);

	}
}
