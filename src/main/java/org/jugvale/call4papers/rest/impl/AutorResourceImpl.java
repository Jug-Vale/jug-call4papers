package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.service.impl.AutorService;

public class AutorResourceImpl implements AutorResource {

	@Inject
	AutorService service;

	public Response criar(Autor autor) {
		service.salvar(autor);
		return recursoCriado(AutorResource.class, autor.getId());
	}

	@Override
	public void apagaPorId(@PathParam("id") Long id) {
		Autor autor = service.buscarPorId(id);
		service.remover(lanca404SeNulo(autor, id));
	}

	@Override
	public Autor buscaPorId(@PathParam("id") Long id) {
		Autor autor = service.buscarPorId(id);
		return lanca404SeNulo(autor, id);
	}

	@Override
	public List<Autor> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void atualizar(@PathParam("id") long id, Autor novoAutor) {
		lanca404SeNulo(buscaPorId(id), id);
		novoAutor.setId(id);
		service.atualizar(novoAutor);
	}

}
