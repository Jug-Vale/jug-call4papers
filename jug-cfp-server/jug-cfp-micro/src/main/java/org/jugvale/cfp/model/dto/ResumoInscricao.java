package org.jugvale.cfp.model.dto;

import org.jugvale.cfp.model.Inscricao;

public class ResumoInscricao {
    
    
    private Long id;
    private Long idParticipante;
    private String nomeParticipante;
    private boolean compareceu;
    private Long idEvento;

    public static ResumoInscricao of(Inscricao inscricao) {
        
        ResumoInscricao resumoInscricao = new ResumoInscricao();
        
        resumoInscricao.id = inscricao.id;
        resumoInscricao.idParticipante = inscricao.participante.id;
        resumoInscricao.nomeParticipante = inscricao.participante.nome;
        resumoInscricao.compareceu = inscricao.compareceu;
        resumoInscricao.idEvento = inscricao.evento.id;
        
        return resumoInscricao;
        
    }

    public Long getId() {
        return id;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public boolean isCompareceu() {
        return compareceu;
    }

    public Long getIdEvento() {
        return idEvento;
    }
    
}
