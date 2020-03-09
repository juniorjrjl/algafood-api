package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        try{
            String corpo = processarTemplate(mensagem);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(emailProperties.getSandBox().getDestinatario());
            helper.setFrom(emailProperties.getRemetente());
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            throw new EmailException("Não foi possível enviar o email", ex);
        }
    }

    
}