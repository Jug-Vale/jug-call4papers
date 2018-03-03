package org.jugvale.cfp.service;

import java.util.List;

public interface Service<T> {
	
	public void salvar(T entidade);

	public void remover(T entidade);

	public List<T> todos();

	public T buscarPorId(long id);

	public T atualizar(T entidade);
}
