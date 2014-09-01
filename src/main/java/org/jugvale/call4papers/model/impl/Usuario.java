package org.jugvale.call4papers.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.enuns.Role;

/**
 * 
 * Classe com os usuários da aplicação.
 * 
 * @author william
 * 
 */
@Entity
@SuppressWarnings("serial")
public class Usuario extends DefaultModel {

	@Column
	private String login;

	@Column
	private String senha;

	@Column
	@Enumerated(EnumType.STRING)
	private Role role;

	public Usuario() {
	}

	public Usuario(String login, String senha, Role role) {
		this.login = login;
		this.senha = senha;
		this.role = role;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(final String senha) {
		this.senha = senha;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (login != null && !login.trim().isEmpty())
			result += "login: " + login;
		if (senha != null && !senha.trim().isEmpty())
			result += ", senha: " + senha;
		return result;
	}
}
