package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CidadeControllerV2OpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeModelV2> listar();

    @Operation(summary = "Busca uma cidade por id")
    CidadeModelV2 buscar(@Parameter(description = "Id de uma cidade", example = "1", required = true) final Long idCidade);

    @Operation(summary = "Cadastra uma nova cidade")
    CidadeModelV2 adicionar(@RequestBody(description = "Representação de uma nova cidade") final CidadeInputV2 cidadeInput);

    @Operation(summary = "Atualiza uma cidade por id")
    CidadeModelV2 atualizar(@Parameter(description = "Id de uma cidade", example = "1", required = true) final Long idCidade,
                            @RequestBody(description = "Informações para atualizar a cidade") final CidadeInputV2 cidadeInput);

    @Operation(summary = "Exclui uma cidade por id")
    void remover(@Parameter(description = "Id de uma cidade", example = "1", required = true) final Long idCidade);
    
}