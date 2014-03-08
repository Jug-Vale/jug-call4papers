package org.jugvale.call4papers.service.impl;

import javax.ejb.Stateless;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.service.ServiceAbstrato;

@Stateless
public class PaperService extends ServiceAbstrato<Paper>{

	@Override
	protected Class<Paper> retornaTipo() {
		return Paper.class;
	}	

}
