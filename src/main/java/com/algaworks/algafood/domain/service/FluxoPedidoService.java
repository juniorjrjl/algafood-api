package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FluxoPedidoService {

    private EmissaoPedidoService emissaoPedido;

    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(final String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(final String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        pedido.entregar();
    }
    
    @Transactional
    public void cancelar(final String codigo){
        Pedido pedido = emissaoPedido.buscar(codigo);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

}