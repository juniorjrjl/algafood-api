package com.algaworks.algafood.infrastructure.service.email;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public EmailException(final String message, final Throwable cause){
        super(message, cause);
    }
    
    public EmailException(final String message) {
        super(message);
    }

}