package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public CollectionModel<GrupoModel> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1" ) Long idGrupo);

    @ApiOperation("Cadastra um grupo")
    public GrupoModel adicionar(@ApiParam(name = "corpo", 
                                          value = "Representação de um novo grupo") 
                                GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long idGrupo, 
                                @ApiParam(name = "corpo", 
                                          value = "Representação de um grupo com os novos dados") 
                                GrupoInput grupoInput);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long idGrupo);
    
}