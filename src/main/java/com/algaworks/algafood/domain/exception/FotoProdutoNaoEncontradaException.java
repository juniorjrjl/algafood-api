package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    
    public FotoProdutoNaoEncontradaException(final String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(final Long restauranteId, final Long produtoId) {
        this(String.format("Não existe uma foto para o produto %d no restaurante %d", restauranteId, produtoId));
    }
}