package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirma um pedido")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public void confirmar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) 
                          String codigo);

    @ApiOperation("Entrega um pedido")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public void entregar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                         String codigo);
    
    @ApiOperation("Cancela um pedido")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public void cancelar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                         String codigo);
    
}