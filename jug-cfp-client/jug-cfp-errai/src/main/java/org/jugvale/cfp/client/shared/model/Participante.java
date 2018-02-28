package org.jugvale.cfp.client.shared.model;

import javax.persistence.Column;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jugvale.cfp.model.DefaultModel;
import org.jugvale.cfp.model.config.Views;
import org.jugvale.cfp.model.enums.Nivel;

import com.fasterxml.jackson.annotation.JsonView;

@Portable
public class Participante extends DefaultModel {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private String email;
	
	private String rg;
	
	@Column
	@JsonView(Views.Interno.class)
	private String empresa;
	
	private String instituicao;
	
	private Nivel nivel;
	
	public Participante() {
		
	}

	public Participante(String nome, String email, String rg, String empresa,
			String instituicao, Nivel nivel) {
		super();
		this.nome = nome;
		this.email = email;
		this.rg = rg;
		this.empresa = empresa;
		this.instituicao = instituicao;
		this.nivel = nivel;
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
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
}
