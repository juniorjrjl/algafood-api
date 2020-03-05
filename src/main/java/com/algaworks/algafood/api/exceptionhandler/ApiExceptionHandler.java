package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;


    private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno inesperado no sistema. " +
        "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
            return buildResponseBodyWithValidateErrors(ex.getBindingResult(), status, request, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
        }else if (rootCause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
        }
        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, 
                            "O corpo da requisição está inválido. Verifique erro de sintaxe.")
                            .userMessage(MSG_ERRO_GENERICO)
                            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail)
            .userMessage("Página não encontrada")
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
                if (ex instanceof MethodArgumentTypeMismatchException) {
                    return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, 
                        headers, status, request);
                }
            
                return super.handleTypeMismatch(ex, headers, status, request);
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatch( MethodArgumentTypeMismatchException ex, 
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = String.format(
            "O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " + 
            "Corrija e informe um valor compatível com o tipo %s", 
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail).userMessage("Página não encontrada").
            build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, 
        HttpHeaders headers, HttpStatus status, WebRequest request){
        ProblemType problemType = ProblemType.PROPRIEDADE_INVALIDA;
        String detail = String.format("A propriedade '%s' é inválida para o objeto '%s'", 
            ex.getPropertyName(), 
            ex.getReferringClass().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail)
            .userMessage(MSG_ERRO_GENERICO)
            .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String path = ex.getPath().stream()
            .map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
        String detail = String.format("A propriedade '%s' recebeu o valor '%s'," + 
            "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s", 
            path, ex.getValue(), ex.getTargetType().getSimpleName());
            Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO)    
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage())
            .userMessage(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage())
            .userMessage(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICO)
            .userMessage(MSG_ERRO_GENERICO)
            .build();
        ex.printStackTrace();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request){
        return buildResponseBodyWithValidateErrors(ex.getBindingResult(), HttpStatus.BAD_REQUEST, request, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
                return buildResponseBodyWithValidateErrors(ex.getBindingResult(), status, request, ex);
    }

    private ResponseEntity<Object> buildResponseBodyWithValidateErrors(BindingResult bindingResult, HttpStatus status, WebRequest request, Exception ex){
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        
        List<Problem.Object> problemFields = bindingResult.getAllErrors().stream()
            .map(objectError -> {
                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                String name = objectError.getObjectName();
                if (objectError instanceof FieldError ){
                    name = ((FieldError) objectError).getField();
                }
                return Problem.Object.builder()
                    .name(name)
                    .userMessage(message)
                    .build();
            })
            .collect(Collectors.toList());
        
        Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail)
            .userMessage(detail).fields(problemFields).build();
            return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
            if (body == null){
                body = Problem.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .userMessage(MSG_ERRO_GENERICO)
                    .build();
            }else if (body instanceof String){
                body = Problem.builder()
                    .status(status.value())
                    .title((String) body)
                    .userMessage(MSG_ERRO_GENERICO)
                    .build();
            }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder().status(status.value()).type(problemType.getUri())
            .title(problemType.getTitle()).timestamp(OffsetDateTime.now()).detail(detail);
    }

}