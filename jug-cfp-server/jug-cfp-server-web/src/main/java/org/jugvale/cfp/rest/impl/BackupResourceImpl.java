package org.jugvale.cfp.rest.impl;

import static org.jugvale.cfp.rest.utils.RESTUtils.lanca404SeNulo;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Inscricao;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.service.EventoService;
import org.jugvale.cfp.service.PaperService;

/**
 * 
 * Exporta os dados do evento passado como JSON em uma classe gorda
 * 
 * @author william
 *
 */
@Path("backup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BackupResourceImpl {

	@Inject
	PaperService paperService;

	@Inject
	EventoService eventoService;

	@GET
	public Backup realizarBackup(@QueryParam("eventoId") Long eventoId) {
		Evento e = lanca404SeNulo(eventoService.buscarPorId(eventoId), eventoId);
		List<Paper> papers = paperService.listarPapersPorEvento(e);
		List<Inscricao> inscricoes = eventoService.inscritosNoEvento(e);
		Backup backup = new Backup();
		backup.setEvento(e);
		backup.setInscricoes(inscricoes);
		backup.setPapers(papers);
		return backup;
	}

	@POST
	public void subirBackup(Backup backup){
		// TODO: Implementar a subida de Backup!
		
	}
	public static class Backup {

		private Evento evento;
		private List<Paper> papers;
		private List<Inscricao> inscricoes;

		public Evento getEvento() {
			return evento;
		}

		public void setEvento(Evento evento) {
			this.evento = evento;
		}

		public List<Paper> getPapers() {
			return papers;
		}

		public void setPapers(List<Paper> papers) {
			this.papers = papers;
		}

		public List<Inscricao> getInscricoes() {
			return inscricoes;
		}

		public void setInscricoes(List<Inscricao> inscricoes) {
			this.inscricoes = inscricoes;
		}

	}
}
