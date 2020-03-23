package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteResponsavelControllerOpeApi {

    @ApiOperation("Busca os responsáveis por um Restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID de um restaurante", 
                                               example = "1", required = true) Long restauranteId);

    @ApiOperation("Associar forma de pagamento ao restaurante")
    public ResponseEntity<Void> associarUsuario(@ApiParam(value = "ID de um restaurante", 
                                                 example = "1", required = true) Long restauranteId, 
                                       @ApiParam(value = "ID de um usuário", 
                                                 example = "1", required = true) Long usuarioId);
    
    @ApiOperation("Desassociar forma de pagamento ao restaurante")
    public ResponseEntity<Void> desassociarUsuario(@ApiParam(value = "ID de um restaurante", 
                                                    example = "1", required = true) Long restauranteId, 
                                          @ApiParam(value = "ID de um usuário", 
                                                    example = "1", required = true)
                                          Long usuarioId);
    
}