package com.algaworks.algafood.domain.exception;


public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d", formaPagamentoId));
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d para o " +
                           "restaurante %d", 
                           formaPagamentoId, restauranteId));
    }

}