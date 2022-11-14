package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos", description = "Gerencia os grupos")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos")
    CollectionModel<GrupoModel> listar();

    @Operation(summary = "Busca um grupo pelo id")
    GrupoModel buscar(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long idGrupo);

    @Operation(summary = "Cadatra um novo grupo")
    GrupoModel adicionar(@RequestBody(description = "Representação de um novo grupo") final GrupoInput grupoInput);

    @Operation(summary = "Atualiza um grupo pelo id")
    GrupoModel atualizar(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long idGrupo,
                         @RequestBody(description = "Informações para atualizar um grupo") final GrupoInput grupoInput);

    @Operation(summary = "Remove um grupo pelo id")
    void remover(@Parameter(description = "Id de um grupo", example = "1", required = true) final Long idGrupo);
    
}