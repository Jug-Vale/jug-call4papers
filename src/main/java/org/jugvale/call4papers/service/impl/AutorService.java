package org.jugvale.call4papers.service.impl;

import javax.ejb.Stateless;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.service.ServiceAbstrato;

@Stateless
public class AutorService extends ServiceAbstrato<Autor>{

	@Override
	protected Class<Autor> retornaTipo() {
		return Autor.class;
	}

}
