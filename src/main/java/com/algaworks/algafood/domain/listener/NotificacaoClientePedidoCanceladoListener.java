package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event){
        Pedido pedido = event.getPedido();
        envioEmail.enviar(Mensagem.builder()
            .assunto(" - Pedido Cancelado")
            .corpo("pedido-cancelado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
        .build());
    }
    
}