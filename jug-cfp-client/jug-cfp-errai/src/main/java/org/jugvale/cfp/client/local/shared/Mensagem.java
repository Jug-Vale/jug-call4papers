package org.jugvale.cfp.client.local.shared;

public class Mensagem {

	public static enum Tipo {
		ERRO, SUCESSO;
	}

	private String texto;
	private Tipo tipo;
	
	public Mensagem() {
	}

	public Mensagem(String texto, Tipo tipo) {
		super();
		this.texto = texto;
		this.tipo = tipo;
	}
	
	public static Mensagem nova(String texto, Tipo tipo) {
		return new Mensagem(texto, tipo);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
