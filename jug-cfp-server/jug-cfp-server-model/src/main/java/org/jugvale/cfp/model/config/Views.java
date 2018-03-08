package org.jugvale.cfp.model.config;

/**
 * Aqui temos as views para JSON. <br />
 * Com anotações podemos controlar os campos que estarão na resposta de uma request HTTP. 
 * @author william
 *
 */
/**
 * @author william
 *
 */
public class Views {
	/**
	 * Esconde campos privados como e-mail, telefone, rg, etc
	 * 
	 * 
	 * @author william
	 *
	 */
	public static class Publico {
	}

	/**
	 * 
	 * Inclui campos visíveis para todos.
	 * 
	 * @author william
	 *
	 */
	public static class Interno extends Publico {
	}
	
	
	public static class ResumoPaper extends Publico {}

}