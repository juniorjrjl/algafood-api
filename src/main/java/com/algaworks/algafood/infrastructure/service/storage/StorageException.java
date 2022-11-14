package com.algaworks.algafood.infrastructure.service.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public StorageException(final String message, final Throwable cause){
        super(message, cause);
    }
    
    public StorageException(final String message) {
        super(message);
    }

}