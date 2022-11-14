package com.algaworks.algafood.infrastructure.service.report;


public class ReportException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ReportException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReportException(final String message) {
        super(message);
    }

}