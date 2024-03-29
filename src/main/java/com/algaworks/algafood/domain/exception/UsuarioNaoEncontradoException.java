package com.algaworks.algafood.domain.exception;


public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(final String mensagem) {
        super(mensagem);
    }
    
    public UsuarioNaoEncontradoException(final Long usuarioId) {
        this(String.format("Não existe um cadastro de usuário com o código %d", usuarioId));
    }
}