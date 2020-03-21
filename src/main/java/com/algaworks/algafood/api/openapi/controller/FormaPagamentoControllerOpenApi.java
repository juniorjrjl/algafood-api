package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", 
                                                                example = "1" )
                                                      Long id, ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    public FormaPagamentoModel adicionar(@ApiParam(name = "corpo", 
                                                   value = "Representação de uma nova forma de pagamento") 
                                          FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Atualiza uma Forma de Pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", 
                                                   example = "1", required = true)Long id, 
                                         @ApiParam(name = "corpo", 
                                                   value = "Representação de uma forma de pagamento com os novos dados") 
                                         FormaPagamentoInput formaPagamentoInput);

                                         @ApiOperation("Exclui uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)Long id);
    
}