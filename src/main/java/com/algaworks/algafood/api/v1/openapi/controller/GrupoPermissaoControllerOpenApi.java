package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos", description = "Gerencia os grupos")
public interface GrupoPermissaoControllerOpenApi {

    @Operation(summary = "Busca grupo pelo id")
    CollectionModel<PermissaoModel> listar(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long grupoId);

    @Operation(summary = "Associa uma permissão á um grupo de permissões")
    ResponseEntity<Void>  associar(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long grupoId,
                                   @Parameter(description = "Id de uma permissão", example = "1", required = true) final Long permissaoId);

    @Operation(summary = "Desassocia uma permissão á um grupo de permissões")
    ResponseEntity<Void>  desassociar(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long grupoId,
                                      @Parameter(description = "Id de uma permissão", example = "1", required = true) final Long permissaoId);
    
}