package org.jugvale.call4papers.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public interface Service<T> {

	public Response novo(T entity);

	public Response remover(Long id) throws WebApplicationException;

	public Response comId(Long id) throws WebApplicationException;

	public Response todos();

	public Response atualizar(Long id, T entity) throws WebApplicationException;

}
