package br.com.bottinocode.cdpmirante.pessoas;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class TelefonesRepository {
	
	@Inject
	private EntityManager em;	

	public Telefone buscarPorId(long id) {
		return em.find(Telefone.class, id);
	}

	public List<Telefone> buscarPorPessoa(Pessoa pessoa) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Telefone> criteria = cb.createQuery(Telefone.class);
		Root<Telefone> telefone = criteria.from(Telefone.class);
		criteria.select(telefone).where(cb.equal(telefone.get("pessoa"), pessoa.getId()));
		return em.createQuery(criteria).getResultList();
	}

}
