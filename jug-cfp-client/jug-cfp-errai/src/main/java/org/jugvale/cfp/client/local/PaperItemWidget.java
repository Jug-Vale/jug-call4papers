package org.jugvale.cfp.client.local;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseException;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.model.impl.Autor;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.rest.PaperResource;

import com.google.gwt.user.client.TakesValue;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.MouseEvent;

@Templated("/web/PaperItemWidget.html")
public class PaperItemWidget implements TakesValue<Paper>, IsElement {

	@Inject
	@AutoBound
	DataBinder<Paper> paperBinder;

	@Inject
	Caller<PaperResource> paperService;

	@Bound
	@Inject
	@DataField
	@Named("h4")
	HTMLHeadingElement titulo;

	@Bound
	@Inject
	@DataField
	@Named("p")
	HTMLElement descricao;

	@Bound
	@Inject
	@DataField
	@Named("span")
	HTMLElement nota;

	@Inject
	@DataField
	HTMLDivElement votar;

	@Inject
	@DataField
	HTMLDivElement autores;

	@Inject
	private Event<Mensagem> eventoMensagem;

	@Override
	public void setValue(Paper value) {
		for (Autor a : value.getAutores()) {
			HTMLAnchorElement aa = (HTMLAnchorElement) DomGlobal.document.createElement("a");
			aa.href = a.getSite();
			aa.setAttribute("class", "badge badge-info");
			aa.textContent = a.getNome();
			autores.appendChild(aa);
		}
		paperBinder.setModel(value);
	}

	@Override
	public Paper getValue() {
		return paperBinder.getModel();
	}

	@EventHandler("votar")
	public void votar(final @ForEvent("click") MouseEvent evt) {
		paperService.call(r -> {
			eventoMensagem.fire(Mensagem.nova("Voto computado com sucesso!", Tipo.SUCESSO));
			paperBinder.getModel().setNota(paperBinder.getModel().getNota() + 1);
		}, (Messagem, t) -> {
			try {
				throw t;
			} catch (ResponseException e) {
				eventoMensagem.fire(Mensagem.nova("Erro ao votar: " + e.getResponse().getText(), Tipo.ERRO));
			} catch (Throwable e) {
				eventoMensagem.fire(Mensagem.nova("Erro desconhecido ao votar: " + e.getMessage(), Tipo.ERRO));
				e.printStackTrace();
			}
			return false;
		}).votarNoPaper(paperBinder.getModel().getId());
	}

}
