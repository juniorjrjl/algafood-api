package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoModel;

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
    public List<PermissaoModel> listar(@ApiParam(value = "ID de um grupo", example = "1" )
                                       Long grupoId);
    
    @ApiOperation("Associa uma permissão ao grupo")
    public void associar(@ApiParam(value = "ID de um grupo", example = "1" ) Long grupoId, 
                        @ApiParam(value = "ID de uma permissão", example = "1" )Long permissaoId);
    
    @ApiOperation("Desassocia uma permissão ao grupo")
    public void desassociar(@ApiParam(value = "ID de um grupo", example = "1" ) Long grupoId, 
                            @ApiParam(value = "ID de uma permissão", example = "1" )Long permissaoId);
    
}