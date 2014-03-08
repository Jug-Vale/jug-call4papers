package org.jugvale.call4papers.service.impl;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.service.ServiceAbstrato;

public class AutorService extends ServiceAbstrato<Autor>{

	@Override
	protected Class<Autor> retornaTipo() {
		return Autor.class;
	}

}
