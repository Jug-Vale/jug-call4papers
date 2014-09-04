package org.jugvale.call4papers.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.UsuarioBuilder;
import org.jugvale.call4papers.model.enuns.Role;

/**
 * 
 * Classe com os usuários da aplicação.
 * 
 * @author william
 * 
 */
@Entity
@Getter @Setter 
@ToString(callSuper=true) 
@EqualsAndHashCode(callSuper=true)
public class Usuario extends DefaultModel {

	private static final long serialVersionUID = 1L;

	@Column
	private String login;

	@Column
	private String senha;

	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static UsuarioBuilder newUsuario() {
		return new UsuarioBuilder();
	}

	public Usuario() { }

	public Usuario(String login, String senha, Role role) {
		setLogin(login);
		setSenha(senha);
		setRole(role);
	}

}
