package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos", description = "Gerencia os grupos")
public interface UsuarioGrupoControllerOpenApi {

    @Operation(summary = "Busca grupo de permissão de um usuário")
    CollectionModel<GrupoModel> listar(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long usuarioId);

    @Operation(summary = "Associa grupo de permissões a um usuário")
    ResponseEntity<Void> associarGrupo(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long usuarioId,
                                       @Parameter(description = "Id de um grupo", example = "1", required = true) final Long grupoId);

    @Operation(summary = "Desassocia grupo de permissões a um usuário")
    ResponseEntity<Void> desassociarGrupo(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long usuarioId,
                                          @Parameter(description = "Id de um grupo", example = "1", required = true) final Long grupoId);
    
}