package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cozinhas", description = "Gerencia as cozinhas")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista as cozinhas de forma paginada")
    PagedModel<CozinhaModel> listar(@Parameter(hidden = true) final Pageable pageable);

    @Operation(summary = "Busca uma cozinha pelo id")
    CozinhaModel buscar(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCozinha);

    @Operation(summary = "Cadastra uma nova cozinha")
    CozinhaModel adicionar(@RequestBody(description = "Representação de uma nova cozinha") final CozinhaInput cozinhaInput);

    @Operation(summary = "Atualiza uma cozinha pelo id")
    CozinhaModel atualizar(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCozinha,
                           @RequestBody(description = "Informações para atualizar a cozinha") final CozinhaInput cozinhaInput);

    @Operation(summary = "Remove uma cozinha pelo id")
    void remover(@Parameter(description = "Id de uma cozinha", example = "1", required = true) final Long idCozinha);
    
}