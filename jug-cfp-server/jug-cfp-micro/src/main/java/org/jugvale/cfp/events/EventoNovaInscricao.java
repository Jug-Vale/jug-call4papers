package org.jugvale.cfp.events;

import org.jugvale.cfp.model.Inscricao;

public class EventoNovaInscricao {
    
    private Inscricao inscricao;

    public EventoNovaInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

}