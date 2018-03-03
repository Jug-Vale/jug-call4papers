package org.jugvale.cfp.service;

import java.util.List;

import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Inscricao;
import org.jugvale.cfp.model.impl.Participante;

public interface EventoService extends Service<Evento> {

	Inscricao inscreverParticipante(Evento evento, Participante participante);

	Inscricao buscaInscricao(Evento evento, Participante participante);

	List<Inscricao> inscritosNoEvento(Evento evento);

}
