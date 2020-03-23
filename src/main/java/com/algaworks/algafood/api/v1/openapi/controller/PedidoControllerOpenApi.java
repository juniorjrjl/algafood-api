package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
        name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Lista os pedidos")
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);
    
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
        name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca um pedido por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do pedido inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) 
                              String codigo);

    @ApiOperation("Emite um pedido")
    public PedidoModel emitir(PedidoInput pedidoInput);
    
}