package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(final String mensagem) {
        super(mensagem);
    }
    
}