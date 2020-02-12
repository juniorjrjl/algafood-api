package com.algaworks.algafood.domain.exception;

/**
 * PedidoNaoEncontradoException
 */
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public PedidoNaoEncontradoException(Long restauranteId) {
        this(String.format("Não existe um cadastro de pedido com o código %d", restauranteId));
    }

    
}