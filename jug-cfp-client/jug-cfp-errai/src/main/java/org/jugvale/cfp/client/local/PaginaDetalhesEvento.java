package org.jugvale.cfp.client.local;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.databinding.client.components.ListContainer;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.local.shared.DateConverter;
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.rest.EventoResource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.MouseEvent;

@Page(path = "evento/{eventoId}")
@Templated("/web/PaginaEvento.html")
public class PaginaDetalhesEvento {

	@PageState
	long eventoId;

	@Inject
	Caller<EventoResource> eventoService;

	@Inject
	@AutoBound
	DataBinder<Evento> eventoBinder;

	@Inject
	@Bound
	@DataField
	@Named("h2")
	HTMLHeadingElement nome;

	@Bound
	@Inject
	@DataField
	@Named("p")
	HTMLElement descricao;

	@Inject
	@DataField
	@Named("em")
	@Bound(converter = DateConverter.class)
	HTMLElement dataInicio;

	@Inject
	@DataField
	@Named("em")
	@Bound(converter = DateConverter.class)
	HTMLElement dataFim;

	@Inject
	@DataField
	@Named("h4")
	HTMLHeadingElement cabecalhoPalestras;

	@Inject
	@DataField
	@ListContainer("listPapersEvento")
	private ListComponent<Paper, PaperItemWidget> listPapersEvento;

	@Bound
	@Inject
	@DataField
	@Named("p")
	HTMLElement local;

	@Inject
	@DataField
	HTMLButtonElement btnInscricao;

	@Inject
	@DataField
	HTMLButtonElement btnSubmeterPaper;

	@Inject
	TransitionTo<PaginaInscricao> paginaInscricaoEvento;
	
	@Inject
	TransitionTo<PaginaSubmeterPaper> paginaSubmeterPaper;

	@Inject
	private Event<Mensagem> eventoMensagem;

	@PageShown
	public void carregaDados() {
		eventoService.call((Evento e) -> {
			eventoBinder.setModel(e);
			if (!e.isInscricoesAbertas()) {
				btnInscricao.innerHTML = "Inscrições fechadas!";
			}
			if(!e.isAceitandoTrabalhos()) {
				btnSubmeterPaper.innerHTML = "Submissão palestras fechada!";
			}
			btnInscricao.disabled = !e.isInscricoesAbertas();
			btnSubmeterPaper.disabled = !e.isAceitandoTrabalhos();
		}, this::erroCarregarEvento).buscaPorId(eventoId);
		eventoService.call((List<Paper> papers) -> {
			if (papers.size() > 0) {
				cabecalhoPalestras.textContent = "Vote nas palestras enviadas:";
			}
			listPapersEvento.setValue(papers);
		}, this::erroCarregarPapers).listaPapersPorEvento(eventoId);
	}

	@EventHandler("btnInscricao")
	public void irParaPaginaInscricao(final @ForEvent("click") MouseEvent event) {
		paginaInscricaoEvento.go(pegaParametros());
	}

	@EventHandler("btnSubmeterPaper")
	public void irParaPaginaNovoPaper(final @ForEvent("click") MouseEvent event) {
		paginaSubmeterPaper.go(pegaParametros());
	}

	private Multimap<String, String> pegaParametros() {
		Multimap<String, String> state = HashMultimap.create();
		state.put("eventoId", eventoBinder.getModel().getId().toString());
		state.put("nomeEvento", eventoBinder.getModel().getNome());
		return state;
	}
	
	public boolean erroCarregarEvento(Object obj, Throwable e) {
		eventoMensagem.fire(Mensagem.nova("Erro ao carregar evento: " + e.getMessage(), Tipo.ERRO));
		return false;
	}

	public boolean erroCarregarPapers(Object obj, Throwable e) {
		eventoMensagem.fire(Mensagem.nova("Erro ao carregar palestras: " + e.getMessage(), Tipo.ERRO));
		return false;
	}
}