package org.jugvale.cfp.model.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jugvale.cfp.model.Paper;
import org.jugvale.cfp.model.Tipo;

public class ResumoPaper {

    private String titulo;
    private String descricao;
    private long nota;
    private Long id;
    private boolean aceito;
    private Tipo tipo;
    private List<ResumoAutor> autores = Collections.emptyList();

    public static ResumoPaper of(Paper paper) {
        ResumoPaper resumoPaper = new ResumoPaper();
        resumoPaper.titulo = paper.titulo;
        resumoPaper.descricao = paper.descricao;
        resumoPaper.nota = paper.nota;
        resumoPaper.id = paper.id;
        resumoPaper.aceito = paper.aceito;
        resumoPaper.tipo = paper.tipo;
        resumoPaper.autores = paper.autores.stream().map(ResumoAutor::of).collect(Collectors.toList());
        return resumoPaper;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getNota() {
        return nota;
    }

    public Long getId() {
        return id;
    }

    public boolean isAceito() {
        return aceito;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public List<ResumoAutor> getAutores() {
        return autores;
    }

}
