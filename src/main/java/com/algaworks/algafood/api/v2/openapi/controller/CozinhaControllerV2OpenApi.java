package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cozinhas", description = "Gerencia as cozinhas")
public interface CozinhaControllerV2OpenApi {

    @Operation(summary = "Lista as cozinhas de forma paginada")
    PagedModel<CozinhaModelV2> listar(final Pageable pageable);

    @Operation(summary = "Busca uma cozinha pelo id")
    CozinhaModelV2 buscar(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCidade);

    @Operation(summary = "Cadastra uma nova cozinha")
    CozinhaModelV2 adicionar(@RequestBody(description = "Representação de uma nova cozinha") final CozinhaInputV2 CozinhaInputV2);

    @Operation(summary = "Atualiza uma cozinha pelo id")
    CozinhaModelV2 atualizar(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCidade,
                             @RequestBody(description = "Informações para atualizar a cozinha")final CozinhaInputV2 CozinhaInputV2);

    @Operation(summary = "Remove uma cozinha pelo id")
    void remover(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCidade);
    
}