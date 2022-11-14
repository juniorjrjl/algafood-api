package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeModel> listar();

    @Operation(summary = "Busca uma cidade por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Id da cidade inválido",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    CidadeModel buscar(final @Parameter(description = "Id de uma cidade", example = "1", required = true) Long idCidade);

    @Operation(summary = "Cadastra uma nova cidade")
    CidadeModel adicionar(@RequestBody(description = "Representação de uma nova cidade") final CidadeInput cidadeInput);

    @Operation(summary = "Atualiza uma cidade por id")
    CidadeModel atualizar(@Parameter(description = "Id de uma cidade", example = "1", required = true) final Long idCidade,
                          @RequestBody(description = "Informações para atualizar a cidade") final CidadeInput cidadeInput);

    @Operation(summary = "Exclui uma cidade por id")
    void remover(@Parameter(description = "Id de uma cidade", example = "1", required = true) final Long idCidade);
    
}