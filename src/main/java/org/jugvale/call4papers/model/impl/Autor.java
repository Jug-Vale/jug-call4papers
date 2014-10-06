package org.jugvale.call4papers.model.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.AutorBuilder;

@Entity
@XmlRootElement
public class Autor extends DefaultModel {

	private static final long serialVersionUID = 2467078273125922962L;

	@Column(nullable = false)
	private String nome;

	@Column(unique = true)
	private String email;

	@Column
	private String telefone;

	@Column
	private String site;

	@Column
	private String miniCurriculo;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private Usuario usuario;

	public static AutorBuilder newAutor() {
		return new AutorBuilder();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getMiniCurriculo() {
		return miniCurriculo;
	}

	public void setMiniCurriculo(String miniCurriculo) {
		this.miniCurriculo = miniCurriculo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}