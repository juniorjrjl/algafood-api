package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        if (!pedido.getStatus().equals(StatusPedido.CRIADO)){
            
        }
        pedido.confirmar();
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