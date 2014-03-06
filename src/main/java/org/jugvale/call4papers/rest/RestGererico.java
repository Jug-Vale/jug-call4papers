package org.jugvale.call4papers.rest;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface RestGererico<T> {

	Response create(T entidade);
	void deleteById(@PathParam("id") Long id);
	T findById(@PathParam("id") Long id);
	List<T> listAll();
	void update(@PathParam("id") long id, T entidade);
	T verificaSeEhNulo(T entidade, long id);

}
