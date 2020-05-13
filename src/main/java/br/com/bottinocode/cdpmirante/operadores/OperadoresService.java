package br.com.bottinocode.cdpmirante.operadores;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

@Stateless
public class OperadoresService {
	
	@Inject
	private Logger log;
	
	@Inject
    private Event<Operador> event;
 
    @Inject
	private EntityManager em;
	
	@Inject
	private OperadoresRepository repositorio;
    
    public void salvar(Operador operador) {
		log.info("Salvando " + operador.getNome());
		
		if(operador.getId() == null || repositorio.buscarPorId(operador.getId()) == null) {
			em.persist(operador);
		} else {
			em.merge(operador);	
		}
		
		event.fire(operador);

	}

	public void excluir(Long id) {
		Operador operador = repositorio.buscarPorId(id);
		
		if(operador == null) {
			throw new NotFoundException();
		}
		
		log.info("Excluindo " + operador.getNome());
		em.remove(operador);		
	}
}
