package com.algaworks.algafood.api.v2.openapi.controller;

import javax.validation.Valid;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades")
    public CollectionModel<CidadeModelV2> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CidadeModelV2 buscar(@PathVariable Long idCidade);

    @ApiOperation("Cadastra uma cidade")
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CidadeModelV2 atualizar(@PathVariable Long idCidade, @RequestBody @Valid CidadeInputV2 cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public void remover(@PathVariable Long idCidade);
    
}