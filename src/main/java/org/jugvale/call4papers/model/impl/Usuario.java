package org.jugvale.call4papers.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlTransient;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.UsuarioBuilder;
import org.jugvale.call4papers.model.enums.Role;
import org.jugvale.call4papers.util.SenhaUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * Classe com os usuários da aplicação.
 * 
 * @author william
 * 
 */
@Entity
public class Usuario extends DefaultModel {

	private static final long serialVersionUID = 1L;

	@Column
	private String login;

	@Column
	@XmlTransient
	@JsonIgnore
	private String senha;

	@Column
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	@XmlTransient
	private Role role;

	public static UsuarioBuilder newUsuario() {
		return new UsuarioBuilder();
	}

	public static UsuarioBuilder administrador() {
		return new UsuarioBuilder(Role.ADMINISTRADOR);
	}

	public static UsuarioBuilder anonimo() {
		return new UsuarioBuilder(Role.ANONIMO);
	}

	public Usuario() {
	}

	public Usuario(String login, String senha, Role role) {
		setLogin(login);
		setSenha(senha);
		setRole(role);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = SenhaUtil.gerarHash(senha);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
