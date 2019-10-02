package org.jugvale.cfp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


@Entity
public class Autor extends PanacheEntity {

	@Column(nullable = false)
	@NotNull(message = "Nome do autor não pode ser nulo")
	@NotEmpty(message = "Nome do autor não pode ser em branco")
	public String nome;

	@Column(unique = true)
	@NotNull(message = "E-mail do autor não pode ser nulo")
	@NotEmpty(message = "E-mail do autor não pode ser em branco")
	public String email;

	@Column
	@NotNull(message = "Telefone do autor não pode ser nulo")
	@NotEmpty(message = "Telefone do autor não pode ser em branco")
	public String telefone;

	@Column
	@NotNull(message = "Site do autor não pode ser nulo")
	@NotEmpty(message = "Site do autor não pode ser em branco")
	public String site;

	@Column(length=1000)
	@NotNull(message = "Mini Currículo do autor não pode ser nulo")
	@NotEmpty(message = "Mini Currículo do autor não pode ser em branco")
	@Size(min = 20, max = 1000, message = "Forneça um mini currículo com tamanho entre 20 e 1000")
	public String miniCurriculo;

}