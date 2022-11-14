package com.algaworks.algafood.domain.exception;


public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(final String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradaException(final Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d", formaPagamentoId));
    }

    public FormaPagamentoNaoEncontradaException(final Long formaPagamentoId, final Long restauranteId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d para o " +
                           "restaurante %d", 
                           formaPagamentoId, restauranteId));
    }

}