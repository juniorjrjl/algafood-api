package com.algaworks.algafood.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao", paramType = "query", 
                          type = "string", allowableValues = "apenas-nome")
    })
    public CollectionModel<RestauranteBasicoModel> listarResumido();

    @ApiIgnore
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
    
    @ApiOperation("Busca um Restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", 
                                             example = "1", required = true) Long id);

    @ApiOperation("Cadastra um Restaurante")
    public RestauranteModel adicionar(RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um Restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id, 
                                      RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um Restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    // TODO: pesquisar a melhor forma de documentar o corpo patch
    public RestauranteModel atualizarParcial(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id, 
                                            Map<String, Object>campos, HttpServletRequest request);

    @ApiOperation("Ativa um Restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Ativa vários Restaurantes por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> ativar(List<Long> restaurantesIds);

    @ApiOperation("Exclui um Restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Exclui vários Restaurantes por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> inativar(List<Long> restaurantesIds);

    @ApiOperation("Abre um Restaurantes por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Fecha um Restaurantes por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrado", response = Problem.class)
    })
    public ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);
    
}