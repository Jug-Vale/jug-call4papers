package org.jugvale.cfp.client.local.paginas.paper;

import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.model.impl.Autor;

import com.google.gwt.user.client.TakesValue;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTextAreaElement;

@Templated("PaginaSubmeterPaper.html#formAutor")
public class FormAutor implements IsElement, TakesValue<Autor> {
	
	@Inject
	@AutoBound
	DataBinder<Autor> autorBinder;
	
	@Inject
	@DataField
	@Bound(property ="nome")
	private HTMLInputElement nomeAutor;
	
	@Bound
	@Inject
	@DataField
	private HTMLInputElement email;
	
	@Bound
	@Inject
	@DataField
	private HTMLInputElement telefone;
	
	@Bound
	@Inject
	@DataField
	private HTMLInputElement site;
	
	@Bound
	@Inject
	@DataField
	private HTMLTextAreaElement miniCurriculo;
	
	@Inject
	@DataField
	private HTMLFormElement formAutor;

	@Override
	public void setValue(Autor value) {
		autorBinder.setModel(value);
	}

	@Override
	public Autor getValue() {
		return autorBinder.getModel();
	}

	@Override
	public HTMLElement getElement() {
		return formAutor;
	}

}
