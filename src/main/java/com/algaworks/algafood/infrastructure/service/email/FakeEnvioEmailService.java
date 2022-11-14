package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{

    public FakeEnvioEmailService(final EmailProperties emailProperties,
                                 final JavaMailSender mailSender,
                                 final Configuration freemarkerConfig) {
        super(emailProperties, mailSender, freemarkerConfig);
    }

    @Override
    public void enviar(final Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);
        log.info(mensagem.getDestinatarios().stream().map(e -> e).reduce(";", String::concat));
        log.info(emailProperties.getRemetente());
        log.info(mensagem.getAssunto());
        log.info(corpo);
    }

    
}