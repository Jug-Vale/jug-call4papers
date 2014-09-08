package org.jugvale.call4papers.model.builder;

import org.jugvale.call4papers.model.enuns.Role;
import org.jugvale.call4papers.model.impl.Usuario;

/**
 *
 * @author Pedro Hos
 *
 */
public class UsuarioBuilder {

	private Usuario usuario;
	
	public UsuarioBuilder() {
		this.usuario = new Usuario();
	}
	
	public UsuarioBuilder(Role role) {
		this.usuario = new Usuario();
		this.usuario.setRole(role);
	}
	
	public Usuario build() {
		return usuario;
	}
	
	public UsuarioBuilder comLogin(String login) {
		usuario.setLogin(login);
		return this;
	}
	
	public UsuarioBuilder comSenha(String senha) {
		usuario.setSenha(senha);
		return this;
	}
	
	public UsuarioBuilder administrador() {
		usuario.setRole(Role.ADMINISTRADOR);
		return this;
	}
	
	public UsuarioBuilder autor() {
		usuario.setRole(Role.AUTOR);
		return this;
	}
	
	public UsuarioBuilder anonimo() {
		usuario.setRole(Role.ANONIMO);
		return this;
	}
}
