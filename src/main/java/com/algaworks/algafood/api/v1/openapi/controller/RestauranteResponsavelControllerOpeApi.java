package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteResponsavelControllerOpeApi {

    @Operation(summary = "Busca responsaveis por um restaurante")
    CollectionModel<UsuarioModel> listar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId);

    @Operation(summary = "Define um usuário como responsável por um restaurante")
    ResponseEntity<Void> associarUsuario(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                         @Parameter(description = "Id de um usuário", example = "1", required = true) final Long usuarioId);

    @Operation(summary = "Desassocia um usuário da direção de um restaurante")
    ResponseEntity<Void> desassociarUsuario(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                            @Parameter(description = "Id de um usuário", example = "1", required = true) final Long usuarioId);
    
}