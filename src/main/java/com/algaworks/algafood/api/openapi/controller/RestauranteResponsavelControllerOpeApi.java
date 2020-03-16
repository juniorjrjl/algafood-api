package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;

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
    public List<UsuarioModel> listar(@ApiParam(value = "ID de um restaurante", 
                                               example = "1", required = true) Long restauranteId);

    @ApiOperation("Associar forma de pagamento ao restaurante")
    public void associarFormaPagamento(@ApiParam(value = "ID de um restaurante", 
                                                 example = "1", required = true) Long restauranteId, 
                                       @ApiParam(value = "ID de um usuário", 
                                                 example = "1", required = true) Long usuarioId);
    
    @ApiOperation("Desassociar forma de pagamento ao restaurante")
    public void desassociarFormaPagamento(@ApiParam(value = "ID de um restaurante", 
                                                    example = "1", required = true) Long restauranteId, 
                                          @ApiParam(value = "ID de um usuário", 
                                                    example = "1", required = true)
                                          Long usuarioId);
    
}