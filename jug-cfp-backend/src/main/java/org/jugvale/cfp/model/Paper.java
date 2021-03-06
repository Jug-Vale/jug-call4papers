package org.jugvale.cfp.model;

import static javax.persistence.FetchType.EAGER;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jugvale.cfp.model.dto.ResumoPaper;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * 
 * A classe de modelo para o Paper <br>
 * Aqui colocamos infomações a respeito do Paper para o Evento.
 * 
 * @author william
 * 
 */
@Entity
public class Paper extends PanacheEntity {

	@Column
	@NotNull(message = "Título da proposta não pode ser nulo")
	@NotEmpty(message = "Título da proposta não pode ser vazio")
	public String titulo;

	@Column(length = 1000)
	@NotEmpty(message = "A proposta precisa de uma descrição!")
	@NotNull(message = "A descrição da proposta não pode ser nula")
	@Size(min = 50, max = 1000, message = "O texto da descrição deve conter entre 50 e 1000 caracteres")
	public String descricao;

	@Column
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Date dataSubmissao = new Date();

	@Column
	public long nota;

	@Column
	public boolean aceito;

	@ManyToMany(fetch = EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "AUTOR_PAPER", joinColumns = { @JoinColumn(name = "PAPER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "AUTOR_ID") })
	public Set<Autor> autores = new HashSet<Autor>();

	@ManyToOne
	public Evento evento;

	@Column
	@Enumerated(EnumType.STRING)
	public Tipo tipo;
	
	public static List<ResumoPaper> porEvento(Evento evento) {
	    return Paper.find("evento", evento).stream()
	                                       .map(p -> (Paper) p)
	                                       .map(ResumoPaper::of)
	                                       .collect(Collectors.toList());
	}
	
   public static List<ResumoPaper> porAutor(Autor autor) {
        return Paper.find("autor", autor).stream()
                                         .map(p -> (Paper) p)
                                         .map(ResumoPaper::of)
                                         .collect(Collectors.toList());
    }

}