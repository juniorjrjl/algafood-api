package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(final String mensagem) {
        super(mensagem);
    }
    
    public ProdutoNaoEncontradoException(final Long produtoId) {
        this(String.format("Não existe um cadastro de produto com o código %d", produtoId));
    }

    public ProdutoNaoEncontradoException(final Long restauranteId, final Long produtoId) {
        this(String.format("Não existe um cadastro de produto com o código %d no restaurante %d", 
            produtoId, restauranteId));
    }

}