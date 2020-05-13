package br.com.bottinocode.cdpmirante.pessoas;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

@Stateless
public class PessoasService {

	@Inject
	private Logger log;

	@Inject
	private Event<Pessoa> event;

	@Inject
	private PessoasRepository pessoaRepositorio;

	@Inject
	private EntityManager em;

	public Pessoa salvar(Pessoa pessoa) {
		log.info("Salvando " + pessoa.getNome());

		if (pessoa.getId() != null && pessoaRepositorio.buscarPorId(pessoa.getId())  != null) {
			pessoa = em.merge(pessoa);
		}
		em.persist(pessoa);
		event.fire(pessoa);
		
		return pessoa;
	}

	public void excluir(Long id) {
		Pessoa pessoa = pessoaRepositorio.buscarPorId(id);

		if (pessoa == null) {
			throw new NotFoundException();
		}

		log.info("Excluindo " + pessoa.getNome());
		em.remove(pessoa);
	}

}
