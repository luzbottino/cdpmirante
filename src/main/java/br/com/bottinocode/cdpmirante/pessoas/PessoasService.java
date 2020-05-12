package br.com.bottinocode.cdpmirante.pessoas;


import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import br.com.bottinocode.cdpmirante.operadores.Operador;

public class PessoasService {
	
	@Inject
	private Logger log;
	
	@Inject
    private Event<Pessoa> event;
 
    @Inject
    private EntityManager em;
    
    public void salvar(Pessoa pessoa) {
		log.info("Salvando " + pessoa.getNome());
		
		if(pessoa.getId() == null || em.find(Operador.class, pessoa.getId()) == null) {
			em.persist(pessoa);
		} else {
			em.merge(pessoa);	
		}
		
		event.fire(pessoa);

	}

	public void excluir(Long id) {
		Pessoa pessoa = em.find(Pessoa.class, id);
		
		if(pessoa == null) {
			throw new NotFoundException();
		}
		
		log.info("Excluindo " + pessoa.getNome());
		em.remove(pessoa);		
	}

}
