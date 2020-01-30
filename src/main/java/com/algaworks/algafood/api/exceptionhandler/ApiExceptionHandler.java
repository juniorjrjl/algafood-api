package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex){
        Problema problema = Problema.builder()
            .dataHora(LocalDateTime.now())
            .mensagem(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException ex){
        Problema problema = Problema.builder()
            .dataHora(LocalDateTime.now())
            .mensagem(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> tratarHttpMediaTypeException(){
        Problema problema = Problema.builder()
            .dataHora(LocalDateTime.now())
            .mensagem("O tipo de mídia não é aceito").build();
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex){
        Problema problema = Problema.builder()
            .dataHora(LocalDateTime.now())
            .mensagem(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
    }

}