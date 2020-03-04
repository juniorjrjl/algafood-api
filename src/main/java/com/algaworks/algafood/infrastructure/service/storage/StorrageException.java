package com.algaworks.algafood.infrastructure.service.storage;

public class StorrageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public StorrageException(String message, Throwable cause){
        super(message, cause);
    }
    
    public StorrageException(String message) {
        super(message);
    }

}