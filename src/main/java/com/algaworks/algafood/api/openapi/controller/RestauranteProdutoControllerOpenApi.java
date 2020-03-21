package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Busca os produtos de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public CollectionModel<ProdutoModel> listar(@ApiParam(value = "ID de um restaurante", 
                                               example = "1",required = true)
                                     Long restauranteId, 
                                     @ApiParam(value = "Incluir produtos inativos", 
                                               example = "true",required = true)
                                     Boolean incluirInativos);

    @ApiOperation("Busca um Produto por ID do restaurante e ID do produto")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
    })
    public ProdutoModel buscar(@ApiParam(value = "ID de um restaurante", 
                                         example = "1",required = true)
                               Long restauranteId, 
                               @ApiParam(value = "ID de um produto", 
                                         example = "1",required = true)
                               Long produtoId);

    @ApiOperation("Cadastra um produto")
    public ProdutoModel cadastrar(@ApiParam(value = "ID de um restaurante", 
                                            example = "1",required = true) Long restauranteId, 
                                  ProdutoInput produtoInput);

    @ApiOperation("Atualiza um produto por ID do restaurante e ID do produto")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Produtro não encontrado", response = Problem.class)
    })
    public ProdutoModel atualizar(@ApiParam(value = "ID de um restaurante", 
                                            example = "1",required = true) Long restauranteId, 
                                  @ApiParam(value = "ID de um produto", 
                                            example = "1",required = true)
                                  Long produtoId, 
                                  ProdutoInput produtoInput);

    @ApiOperation("Exclui um Produto por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de um restaurante", 
                                  example = "1",required = true)
                        Long restauranteId, 
                        @ApiParam(value = "ID de um produto", 
                                  example = "1",required = true)
                        Long produtoId);
    
}