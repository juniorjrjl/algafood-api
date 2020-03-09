package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);
        log.info(mensagem.getDestinatarios().stream().map(e -> e).reduce(";", String::concat));
        log.info(emailProperties.getRemetente());
        log.info(mensagem.getAssunto());
        log.info(corpo);
    }

    
}