package org.jugvale.call4papers.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;

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

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Usuario) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
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
