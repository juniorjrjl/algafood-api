package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(final String mensagem) {
        super(mensagem);
    }
    
    public CidadeNaoEncontradaException(final Long cidadeId) {
        this(String.format("Não existe um cadastro de cidade com o código %d", cidadeId));
    }
}