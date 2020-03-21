package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões de um grupo")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID de um grupo", example = "1" )
                                       Long grupoId);
    
    @ApiOperation("Associa uma permissão ao grupo")
    public ResponseEntity<Void>  associar(@ApiParam(value = "ID de um grupo", example = "1" ) Long grupoId, 
                        @ApiParam(value = "ID de uma permissão", example = "1" )Long permissaoId);
    
    @ApiOperation("Desassocia uma permissão ao grupo")
    public ResponseEntity<Void>  desassociar(@ApiParam(value = "ID de um grupo", example = "1" ) Long grupoId, 
                            @ApiParam(value = "ID de uma permissão", example = "1" )Long permissaoId);
    
}