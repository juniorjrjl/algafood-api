package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService{

    @Autowired
    protected EmailProperties emailProperties;

    @Autowired
    protected JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try{
            String corpo = processarTemplate(mensagem);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setFrom(emailProperties.getRemetente());
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            throw new EmailException("Não foi possível enviar o email", ex);
        }
    }

    protected String processarTemplate(Mensagem mensagem){
        try{
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, 
                mensagem.getVariaveis());
        }catch (Exception ex){
            throw new EmailException("Não foi possível montar o template do email", ex);
        }
    }
    
}