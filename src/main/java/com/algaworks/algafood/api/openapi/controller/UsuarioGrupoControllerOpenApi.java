package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public CollectionModel<GrupoModel> listar(@ApiParam(value = "ID de um usuário", example = "1",required = true) Long usuarioId);

    @ApiOperation("Associa um Grupo a um Usuario")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> associarGrupo(@ApiParam(value = "ID de um usuário", example = "1",required = true)
                              Long usuarioId,
                              @ApiParam(value = "ID de um Grupo", example = "1",required = true)
                              Long grupoId);

    @ApiOperation("Desassocia um Grupo de um Usuário")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> desassociarGrupo(@ApiParam(value = "ID de um usuário", example = "1",required = true)
                                 Long usuarioId,
                                 @ApiParam(value = "ID de um Grupo", example = "1",required = true)
                                 Long grupoId);
    
}