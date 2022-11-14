package com.algaworks.algafood.domain.exception;


public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(final String mensagem) {
        super(mensagem);
    }
    
    public GrupoNaoEncontradoException(final Long grupoId) {
        this(String.format("Não existe um cadastro de grupo com o código %d", grupoId));
    }
}