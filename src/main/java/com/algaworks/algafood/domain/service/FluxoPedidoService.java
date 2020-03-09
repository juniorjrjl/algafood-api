package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        if (!pedido.getStatus().equals(StatusPedido.CRIADO)){
            
        }
        pedido.confirmar();
        /*envioEmail.enviar(Mensagem.builder()
            .assunto(" - Pedido confirmado")
            .corpo("O pedido de código <strong>" + pedido.getCodigo() + "</strong> foi confirmado")
            .destinatario(pedido.getCliente().getEmail())
            .build()
            );*/
    }

    @Transactional
    public void entregar(String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        pedido.entregar();
    }
    
    @Transactional
    public void cancelar(String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        pedido.cancelar();
    }

}