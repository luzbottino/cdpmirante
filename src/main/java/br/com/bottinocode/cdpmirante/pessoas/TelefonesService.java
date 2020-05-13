package br.com.bottinocode.cdpmirante.pessoas;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

@Stateless
public class TelefonesService {

    @Inject
    private Event<Telefone> event;
    
    @Inject
    private PessoasRepository repositorioPessoa;

    @Inject
    private TelefonesRepository repositorioTelefone;

    @Inject
    private EntityManager em;

    
    public void salvar(Telefone telefone, Pessoa pessoa) {
        if (telefone.getId() == null || repositorioTelefone.buscarPorId(telefone.getId()) == null) {            
            telefone.setPessoa(repositorioPessoa.buscarPorId(pessoa.getId()));
            em.persist(telefone);
        } else {
            em.merge(telefone);
        }

        event.fire(telefone);

    }

    public void excluir(Long id) {
		Telefone telefone = repositorioTelefone.buscarPorId(id);

		if (telefone == null) {
			throw new NotFoundException();
        }
        	
		em.remove(telefone);
	}

}