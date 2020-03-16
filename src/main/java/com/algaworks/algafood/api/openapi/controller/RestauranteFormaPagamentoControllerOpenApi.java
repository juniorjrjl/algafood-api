package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Busca as formas de pagamentos aceitas por um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public List<FormaPagamentoModel> listar(@ApiParam(value = "ID de um restaurante", 
                                                      example = "1", required = true)
                                            Long restauranteId);
    @ApiOperation("Associa ao restaurante uma forma de pagamento")
    public void associarFormaPagamento(@ApiParam(value = "ID de um restaurante", 
                                                 example = "1", required = true)
                                       Long restauranteId, 
                                       @ApiParam(value = "ID de uma Forma de Pagamento", 
                                                      example = "1", required = true)
                                       Long formaPagamentoId);

    @ApiOperation("Desassocia ao restaurante uma forma de pagamento") 
    public void desassociarFormaPagamento(@ApiParam(value = "ID de um restaurante", 
                                                    example = "1", required = true)
                                          Long restauranteId, 
                                          @ApiParam(value = "ID de uma Forma de Pagamento", 
                                                      example = "1", required = true)
                                          Long formaPagamentoId);
    
}