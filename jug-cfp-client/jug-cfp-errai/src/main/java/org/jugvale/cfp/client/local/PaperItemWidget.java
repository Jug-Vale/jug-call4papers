package org.jugvale.cfp.client.local;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.model.impl.Paper;
import com.google.gwt.user.client.TakesValue;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;

@Templated("/web/PaperItemWidget.html")
public class PaperItemWidget implements TakesValue<Paper>, IsElement {

	@Inject
	@AutoBound
	DataBinder<Paper> paperBinder;

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
	
	@Override
	public void setValue(Paper value) {
		paperBinder.setModel(value);
	}

	@Override
	public Paper getValue() {
		return paperBinder.getModel();
	}

}
