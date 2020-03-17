package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    public PagedModel<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da Cozinha inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel buscar(@ApiParam(value = "ID de uma Cozinha", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma Cozinha")
    public CozinhaModel adicionar(@ApiParam(name = "corpo", 
                                            value = "Representação de uma nova cozinha") 
                                  CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel atualizar(@ApiParam(value = "ID de uma Cozinha", example = "1", required = true) Long id, 
                                  @ApiParam(name = "corpo", 
                                            value = "Representação de uma cozinha com os novos dados") 
                                  CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de uma Cozinha", example = "1", required = true) Long id);
    
}