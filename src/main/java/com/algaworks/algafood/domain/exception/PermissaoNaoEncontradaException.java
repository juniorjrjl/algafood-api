package com.algaworks.algafood.domain.exception;


public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(final String mensagem) {
        super(mensagem);
    }
    
    public PermissaoNaoEncontradaException(final Long permissaoId) {
        this(String.format("Não existe um cadastro de permissao com o código %d", permissaoId));
    }
}