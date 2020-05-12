package br.com.bottinocode.cdpmirante.operadores;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class OperadoresRepository {	

	@Inject
	private EntityManager em;	

	public Operador buscarPorId(long id) {
		return em.find(Operador.class, id);
	}

	public List<Operador> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Operador> criteria = cb.createQuery(Operador.class);
		Root<Operador> operador = criteria.from(Operador.class);
		criteria.select(operador).orderBy(cb.asc(operador.get("nome")));
		return em.createQuery(criteria).getResultList();
	}

	public Operador buscarPorLoginSenha(String login, String senha) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Operador> criteria = cb.createQuery(Operador.class);
		Root<Operador> operador = criteria.from(Operador.class);
		
		Predicate porLogin = cb.equal(operador.get("login"), login);
		Predicate porSenha = cb.equal(operador.get("senha"), senha);
		
		Predicate porLoginSenha = cb.and(porLogin, porSenha);		
		
		criteria.select(operador)
		.where(porLoginSenha);
		
		return em.createQuery(criteria).getSingleResult();
		
	}

}
