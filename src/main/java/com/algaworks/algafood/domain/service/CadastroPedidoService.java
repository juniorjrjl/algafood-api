package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar(Long id){
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }
    
}