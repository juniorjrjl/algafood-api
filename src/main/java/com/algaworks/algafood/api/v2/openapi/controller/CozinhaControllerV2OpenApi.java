package com.algaworks.algafood.api.v2.openapi.controller;

import javax.validation.Valid;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {

    @ApiOperation("Lista as cozinhas")
    public PagedModel<CozinhaModelV2> listar(Pageable pageable);

    @ApiOperation("Busca uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da Cozinha inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModelV2 buscar(@PathVariable Long idCidade);

    @ApiOperation("Cadastra uma Cozinha")
    public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 CozinhaInputV2);

    @ApiOperation("Atualiza uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModelV2 atualizar(@PathVariable Long idCidade, @RequestBody @Valid CozinhaInputV2 CozinhaInputV2);

    @ApiOperation("Exclui uma Cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(@PathVariable Long idCidade);
    
}