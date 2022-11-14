package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista os restaurantes",
            parameters = @Parameter(
                    name = "projecao",
                    description = "Nome da projeção",
                    in = QUERY,
                    example = "apenas-nome"))
    CollectionModel<RestauranteBasicoModel> listarResumido();

    @Operation(summary = "Lista os nomes de restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @Operation(summary = "Busca restaurante pelo id")
    RestauranteModel buscar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante);

    @Operation(summary = "Cadastra um novo restaurante")
    RestauranteModel adicionar(@RequestBody(description = "Representação de um novo restaurante") final RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza um restaurante por id")
    RestauranteModel atualizar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante,
                               @RequestBody(description = "Informações para atualizar um restaurante") final RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza um restaurante por id de forma parcial")
    RestauranteModel atualizarParcial(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante,
                                      final Map<String, Object>campos,
                                      final HttpServletRequest request);

    @Operation(summary = "Ativa um restaurante por id")
    ResponseEntity<Void> ativar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante);

    @Operation(summary = "Ativa resutarantes por id")
    ResponseEntity<Void> ativar(final List<Long> restaurantesIds);

    @Operation(summary = "Desativa um restaurante por id")
    ResponseEntity<Void> inativar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante);

    @Operation(summary = "Desativa resutarantes por id")
    ResponseEntity<Void> inativar(final List<Long> restaurantesIds);

    @Operation(summary = "Marca um restaurante como aberto")
    ResponseEntity<Void> abrir(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante);

    @Operation(summary = "Marca um restaurante como fechado")
    ResponseEntity<Void> fechar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long idRestaurante);
    
}