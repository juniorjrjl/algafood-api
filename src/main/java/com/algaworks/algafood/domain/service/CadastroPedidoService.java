package com.algaworks.algafood.domain.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurate;

    public Pedido buscar(Long id){
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }
    
    @Transactional
    public Pedido salvar(Pedido pedido){
        Restaurante restaurante = cadastroRestaurate.buscar(pedido.getRestaurante().getId());
        if (restaurante.getFormasPagamento().stream()
            .filter(f -> f.getId() ==     pedido.getFormaPagamento().getId())
            .collect(Collectors.toList()).isEmpty()){

        }
        return pedidoRepository.save(pedido);
    }

}