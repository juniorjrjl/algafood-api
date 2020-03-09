package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event){
        Pedido pedido = event.getPedido();
        envioEmail.enviar(Mensagem.builder()
            .assunto(" - Pedido confirmado")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
        .build());
    }
    
}