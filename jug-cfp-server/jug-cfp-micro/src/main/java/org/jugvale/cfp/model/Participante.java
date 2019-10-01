package org.jugvale.cfp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Participante extends PanacheEntity {

	@Column(nullable = false)
	@NotNull(message = "O nome não pode ser nulo")
	@NotEmpty(message = "O nome não pode ser vazio")
	private String nome;

	@NotNull(message = "Email não pode ser nulo")
	@NotEmpty(message = "Forneça um e-mail")
	@Column(unique = true, nullable = false)
	private String email;

	@Column
	@NotNull(message = "O RG não pode ser nulo")
	@NotEmpty(message = "Forneça um valor para o RG")
	private String rg;

	@Column
	private String empresa;

	@Column
	private String instituicao;

	@Column
	@Enumerated(EnumType.STRING)
	private Nivel nivel;

}
