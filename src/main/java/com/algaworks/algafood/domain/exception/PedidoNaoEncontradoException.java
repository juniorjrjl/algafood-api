package com.algaworks.algafood.domain.exception;

/**
 * PedidoNaoEncontradoException
 */
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    
    public PedidoNaoEncontradoException(final String codigoPedido) {
        super(String.format("Não existe um cadastro de pedido com o código %s", codigoPedido));
    }

    
}