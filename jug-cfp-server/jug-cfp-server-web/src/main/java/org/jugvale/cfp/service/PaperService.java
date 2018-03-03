package org.jugvale.cfp.service;

import java.util.List;

import org.jugvale.cfp.model.impl.Autor;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;

public interface PaperService extends Service <Paper>{

	List<Paper> listarPapersPorAutor(Autor autor);

	List<Paper> listarPapersPorEvento(Evento evento);

}
