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
    
    public void salvar(Operador operador) {
		log.info("Salvando " + operador.getNome());
		
		if(em.find(Operador.class, operador.getId()) == null) {
			em.persist(operador);
		} else {
			em.merge(operador);	
		}
		
		event.fire(operador);

	}
}
