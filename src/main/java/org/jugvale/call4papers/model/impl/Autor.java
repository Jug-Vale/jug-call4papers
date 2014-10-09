package org.jugvale.call4papers.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.AutorBuilder;
import org.jugvale.call4papers.rest.config.Views;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@XmlRootElement
public class Autor extends DefaultModel {

	private static final long serialVersionUID = 2467078273125922962L;

	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private String nome;

	@Column
	@JsonView(Views.Internal.class)	
	private String email;

	@Column
	@JsonView(Views.Internal.class)		
	private String telefone;

	@Column
	@JsonView(Views.Public.class)
	private String site;

	@Column
	@JsonView(Views.Public.class)
	private String miniCurriculo;

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

}