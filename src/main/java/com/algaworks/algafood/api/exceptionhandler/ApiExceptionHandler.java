package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    private final MessageSource messageSource;


    private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno inesperado no sistema. " +
        "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
            return buildResponseBodyWithValidateErrors(ex.getBindingResult(), status, request, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
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
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail)
            .userMessage("Página não encontrada")
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
                                                        final HttpStatus status, final WebRequest request) {
                if (ex instanceof MethodArgumentTypeMismatchException) {
                    return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, 
                        headers, status, request);
                }
            
                return super.handleTypeMismatch(ex, headers, status, request);
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = String.format(
            "O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " + 
            "Corrija e informe um valor compatível com o tipo %s", 
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail).userMessage("Página não encontrada").
            build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(final PropertyBindingException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request){
        ProblemType problemType = ProblemType.PROPRIEDADE_INVALIDA;
        String detail = String.format("A propriedade '%s' é inválida para o objeto '%s'", 
            ex.getPropertyName(), 
            ex.getReferringClass().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail)
            .userMessage(MSG_ERRO_GENERICO)
            .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(final InvalidFormatException ex,
                                                                final HttpHeaders headers,
                                                                final HttpStatus status,
                                                                final WebRequest request) {
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
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(final EntidadeNaoEncontradaException ex,
                                                                  final WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage())
            .userMessage(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(final NegocioException ex, final WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedExceptionException(final AccessDeniedException ex, final WebRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN		;
        Problem problem = createProblemBuilder(status, ProblemType.ACESSO_NEGADO, ex.getMessage())
            .userMessage("Você não tem permissão para acessar este recurso.")
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(final EntidadeEmUsoException ex, final WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage())
            .userMessage(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(final Exception ex, final WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICO)
            .userMessage(MSG_ERRO_GENERICO)
            .build();
        log.error(ExceptionUtils.getStackTrace(ex));
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(final ValidacaoException ex, final WebRequest request){
        return buildResponseBodyWithValidateErrors(ex.getBindingResult(), HttpStatus.BAD_REQUEST, request, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
                                                                      final HttpHeaders headers,
                                                                      final HttpStatus status,
                                                                      final WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
                return buildResponseBodyWithValidateErrors(ex.getBindingResult(), status, request, ex);
    }

    private ResponseEntity<Object> buildResponseBodyWithValidateErrors(final BindingResult bindingResult,
                                                                       final HttpStatus status,
                                                                       final WebRequest request,
                                                                       final Exception ex){
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        
        List<FieldErrorDetails> problemFields = bindingResult.getAllErrors().stream()
            .map(objectError -> {
                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                String name = objectError.getObjectName();
                if (objectError instanceof FieldError ){
                    name = ((FieldError) objectError).getField();
                }
                return FieldErrorDetails.builder()
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
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex,
                                                             final Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        Problem response = Problem.builder()
                .status(status.value())
                .title((Objects.nonNull(body) && body instanceof String title) ? title : status.getReasonPhrase())
                .userMessage(MSG_ERRO_GENERICO)
                .build();
        return super.handleExceptionInternal(ex, (Objects.nonNull(body) ? body : response), headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(final HttpStatus status, final ProblemType problemType, final String detail){
        return Problem.builder().status(status.value()).type(problemType.getUri())
            .title(problemType.getTitle()).timestamp(OffsetDateTime.now()).detail(detail);
    }

}