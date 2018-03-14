package org.jugvale.cfp.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.jugvale.cfp.model.DefaultModel;
import org.jugvale.cfp.model.config.Views;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Autor extends DefaultModel {

	private static final long serialVersionUID = 2467078273125922962L;

	@Column(nullable = false)
	@JsonView(Views.Publico.class)
	@NotNull(message = "Nome do autor não pode ser nulo")
	@NotEmpty(message = "Nome do autor não pode ser em branco")
	private String nome;

	@Column
	@JsonView(Views.Interno.class)
	@NotNull(message = "E-mail do autor não pode ser nulo")
	@NotEmpty(message = "E-mail do autor não pode ser em branco")
	private String email;

	@Column
	@JsonView(Views.Interno.class)
	@NotNull(message = "Telefone do autor não pode ser nulo")
	@NotEmpty(message = "Telefone do autor não pode ser em branco")
	private String telefone;

	@Column
	@JsonView(Views.Publico.class)
	@URL(message = "Forneça uma URL válida")
	@NotNull(message = "Site do autor não pode ser nulo")
	@NotEmpty(message = "Site do autor não pode ser em branco")
	private String site;

	@Column(length=1000)
	@JsonView(Views.Publico.class)
	@NotNull(message = "Mini Currículo do autor não pode ser nulo")
	@NotEmpty(message = "Mini Currículo do autor não pode ser em branco")
	@Length(min = 20, max = 1000, message = "Forneça um mini currículo com tamanho entre 20 e 1000")
	private String miniCurriculo;

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