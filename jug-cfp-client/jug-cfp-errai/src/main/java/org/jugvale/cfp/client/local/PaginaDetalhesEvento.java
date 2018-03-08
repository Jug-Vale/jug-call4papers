package org.jugvale.cfp.client.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.databinding.client.components.ListContainer;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.shared.DateConverter;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.rest.EventoResource;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;

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

	@PageShown
	public void carregaDados() {
		eventoService.call((Evento e) -> eventoBinder.setModel(e), (m, t) -> false).buscaPorId(eventoId);
		eventoService.call((List<Paper> papers) -> {
			if (papers.size() > 0) {
				cabecalhoPalestras.textContent = "Vote nas palestras enviadas:";
			}
			listPapersEvento.setValue(papers);
		}, (m, t) -> false).listaPapersPorEvento(eventoId);
	}
}