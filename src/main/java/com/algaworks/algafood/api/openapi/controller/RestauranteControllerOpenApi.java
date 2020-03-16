package com.algaworks.algafood.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao", paramType = "query", 
                          type = "string", allowableValues = "apenas-nome")
    })
    public List<RestauranteModel> listarResumido();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public List<RestauranteModel> listarApenasNome();
    
    public RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    public RestauranteModel adicionar(RestauranteInput restauranteInput);

    public RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id, 
                                      RestauranteInput restauranteInput);

    // TODO: pesquisar a melhor forma de documentar o patch
    public RestauranteModel atualizarParcial(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id, 
                                            Map<String, Object>campos, HttpServletRequest request);

    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome do restaurante", name = "nome", paramType = "query", 
                            type = "string")
    })
    public List<RestauranteModel> restaurantesFreteGratis(String nome);

    public void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    public void ativar(List<Long> restaurantesIds);

    public void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    public void inativar(List<Long> restaurantesIds);

    public void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    public void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);
    
}