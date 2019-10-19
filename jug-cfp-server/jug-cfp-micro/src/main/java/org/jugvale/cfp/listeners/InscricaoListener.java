package org.jugvale.cfp.listeners;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jugvale.cfp.events.EventoNovaInscricao;
import org.jugvale.cfp.model.Inscricao;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
public class InscricaoListener {
    
    final static String TITULO_EVENTO_PLACEHOLDER = "evento_nome";
    
    final static String NOME_PARTICIPANTE_PLACEHOLDER = "participante_nome";
    
    @Inject
    @ConfigProperty(name = "cfp.mail.title", defaultValue = "Você está inscrito no evento_nome")
    String title;
    
    @Inject
    @ConfigProperty(name = "cfp.mail.body", defaultValue = "Olá participante_nome, inscrição confirmada no evento_nome.\n- JUG Vale")
    String body;
    
    @Inject
    Mailer mailer;
    
    public void novaInscricao(@Observes EventoNovaInscricao novaInscricao) {
        Inscricao inscricao = novaInscricao.getInscricao();
        String titulo = aplica(title, inscricao);
        String corpo = aplica(body, inscricao);
        Mail mail = Mail.withText(inscricao.participante.email, titulo, corpo);
        mailer.send(mail);
    }
    
    
    private String aplica(String texto, Inscricao inscricao) {
        return texto.replaceAll(TITULO_EVENTO_PLACEHOLDER, inscricao.evento.nome)
                    .replaceAll(NOME_PARTICIPANTE_PLACEHOLDER, inscricao.participante.nome);
    }
    
}
