package org.jugvale.call4papers.model.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.EntidadeAbstrata;

@Entity
@XmlRootElement
public class Autor extends EntidadeAbstrata {

	private static final long serialVersionUID = 3253089299207253810L;

	@Column
	private String nome;

	@Column
	private String email;

	@Column
	private String telefone;

	@Column
	private String site;

	@Column
	private String miniCurriculo;

	@ManyToMany
	private Set<Paper> papers = new HashSet<Paper>();

	@OneToOne
	private Usuario usuario;

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
			return id.equals(((Autor) that).id);
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(final String site) {
		this.site = site;
	}

	public String getMiniCurriculo() {
		return this.miniCurriculo;
	}

	public void setMiniCurriculo(final String miniCurriculo) {
		this.miniCurriculo = miniCurriculo;
	}

	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(final Set<Paper> papers) {
		this.papers = papers;
	}

	public EntidadeAbstrata getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		result += "serialVersionUID: " + serialVersionUID;
		if (nome != null && !nome.trim().isEmpty())
			result += ", nome: " + nome;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		if (telefone != null && !telefone.trim().isEmpty())
			result += ", telefone: " + telefone;
		if (site != null && !site.trim().isEmpty())
			result += ", site: " + site;
		if (miniCurriculo != null && !miniCurriculo.trim().isEmpty())
			result += ", miniCurriculo: " + miniCurriculo;
		return result;
	}
}