package org.jugvale.call4papers.rest;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public abstract class RestAbstrato<T> {

	protected abstract Response create(T entidade);
	protected abstract void deleteById(@PathParam("id") Long id);
	protected abstract T findById(@PathParam("id") Long id);
	protected abstract List<T> listAll();
	protected abstract void update(@PathParam("id") long id, T entidade);

	protected T verificaSeEhNulo(T entidade, long id){
		return lanca404SeNulo(entidade, "ID: '" + id + "' não encontrado");
	}

}
