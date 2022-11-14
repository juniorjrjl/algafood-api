package com.algaworks.algafood.domain.exception;


public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(final String mensagem) {
        super(mensagem);
    }
    
    public RestauranteNaoEncontradoException(final Long restauranteId) {
        this(String.format("Não existe um cadastro de restaurante com o código %d", restauranteId));
    }
}