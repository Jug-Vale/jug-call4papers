package org.jugvale.cfp.model.dto;

import org.jugvale.cfp.model.Autor;

public class ResumoAutor {
    
    private String nome;
    private String miniCurriculo;
    private String site;

    public static ResumoAutor of(Autor autor) {
        ResumoAutor resumoAutor = new ResumoAutor();
        resumoAutor.nome = autor.nome;
        resumoAutor.miniCurriculo = autor.miniCurriculo;
        resumoAutor.site = autor.site;
        return resumoAutor;
    }
    public String getNome() {
        return nome;
    }

    public String getMiniCurriculo() {
        return miniCurriculo;
    }

    public String getSite() {
        return site;
    }

}
