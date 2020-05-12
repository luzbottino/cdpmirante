package br.com.bottinocode.cdpmirante.pessoas;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class PessoasRepository {
	
	@Inject
	private EntityManager em;
	
	public Pessoa buscarPorId(long id) {
		return em.find(Pessoa.class, id);
	}

	public List<Pessoa> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = cb.createQuery(Pessoa.class);
		Root<Pessoa> pessoa = criteria.from(Pessoa.class);
		criteria.select(pessoa).orderBy(cb.asc(pessoa.get("nome")));
		return em.createQuery(criteria).getResultList();
	}

}
