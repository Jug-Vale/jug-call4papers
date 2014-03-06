package org.jugvale.call4papers.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 
 * Uma classe Service abstrata para uso com as entidades do nosso sistema
 * 
 * @author william
 * 
 * @param <T>
 *            A entidade alvo usada na implementação dessa classe
 */
@Stateless
public abstract class ServiceAbstrato<T> {
	/**
	 * O tipo especializado na classe de implementação, deve ser retornado na especialização
	 */
	protected Class<T> tipo = retornaTipo();

	@PersistenceContext(unitName = "primary")
	protected EntityManager em;

	public void salvar(T entidade) {
		em.persist(entidade);
	}

	public void remover(T entidade) {
		em.remove(entidade);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscaTodos() {
		CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(tipo));
		return (List<T>) em.createQuery(cq).getResultList();
	}

	public T buscarPorId(long id) {
		return em.find(tipo, id);
	}
	
	public T atualizar(T entidade){
		return em.merge(entidade);
	}
	
	protected abstract Class<T> retornaTipo();
}
