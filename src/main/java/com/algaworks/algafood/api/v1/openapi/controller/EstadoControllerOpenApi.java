package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    public CollectionModel<EstadoModel> listar();

    @ApiOperation("Busca um Estado por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do Estado inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    public EstadoModel buscar(@ApiParam(value = "ID de um Estado", example = "1", required = true) 
                              Long idEstado);

    @ApiOperation("Cadastra um estado")
    public EstadoModel adicionar(EstadoInput estadoInput);

    @ApiOperation("Atualiza um Estado por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    public EstadoModel atualizar(@ApiParam(value = "ID de um Estado", example = "1", required = true)
                                 Long idEstado, 
                                 EstadoInput estadoInput);
             
    @ApiOperation("Exclui um Estado por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de um Estado", example = "1", required = true)Long idEstado);
    
}