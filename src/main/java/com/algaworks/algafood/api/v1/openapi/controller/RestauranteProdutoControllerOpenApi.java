package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante")
    CollectionModel<ProdutoModel> listar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId, final Boolean incluirInativos);

    @Operation(summary = "Busca um produto pelo id e o id do restaurante")
    ProdutoModel buscar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                        @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId);

    @Operation(summary = "Cadastra um produto para um restaurante")
    ProdutoModel cadastrar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                           @RequestBody(description = "Representação de um novo produto") final ProdutoInput produtoInput);

    @Operation(summary = "Atualiza um produto pelo id e o id do restaurante")
    ProdutoModel atualizar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                           @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId,
                           @RequestBody(description = "Informações para atualizar um produto") final ProdutoInput produtoInput);

    @Operation(summary = "Remove um produto pelo id e o id do restaurante")
    void remover(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                 @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId);
    
}