package org.jugvale.cfp.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;

import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

@ApplicationScoped
public class MensagemWidget {
	
	private static class CaixaMensagem extends DialogBox {

		private Label lblMensagem;

		public CaixaMensagem() {
			setAnimationEnabled(true);
			setModal(true);
			setGlassEnabled(true);
			VerticalPanel vp = new VerticalPanel();
			lblMensagem = new Label("");
			lblMensagem.getElement().getStyle().setFontSize(20, Unit.PX);
			Button ok = new Button("OK");
			ok.addClickHandler(e -> CaixaMensagem.this.hide());
			vp.add(lblMensagem);
			vp.add(ok);
			vp.setCellHorizontalAlignment(ok, HasHorizontalAlignment.ALIGN_CENTER);
			setWidget(vp);
			vp.setSpacing(20);
		}

		public void setMessage(String text, Tipo tipo) {
			if (tipo == Tipo.ERRO) {
				setText("Erro!");
				lblMensagem.getElement().getStyle().setColor("red");
			} else if (tipo == Tipo.SUCESSO) {
				setText("Sucesso!");
				lblMensagem.getElement().getStyle().setColor("blue");
			}
			lblMensagem.getElement().setInnerHTML(text);
		}
	}

	CaixaMensagem caixaMensagem = new CaixaMensagem();

	public void receberMensagem(@Observes @Any Mensagem mensagem) {
		caixaMensagem.setMessage(mensagem.getTexto(), mensagem.getTipo());
		caixaMensagem.setPopupPositionAndShow((ow, oh) -> 
			caixaMensagem.setPopupPosition((Window.getScrollLeft() + Window.getClientWidth()) / 2, Window.getScrollTop() + 10)
		);
	}
}