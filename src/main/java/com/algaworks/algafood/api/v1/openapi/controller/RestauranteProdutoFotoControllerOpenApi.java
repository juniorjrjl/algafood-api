package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteProdutoFotoControllerOpenApi {


    @Operation(summary = "Atualiza/Define uma foto para um produto do restaurante")
    FotoProdutoModel atualizarFoto(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                   @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId,
                                   @RequestBody(description = "Informações para atualizar/definir foto de um produto")final FotoProdutoInput fotoProdutoInput) throws IOException;

    @Operation(summary = "Busca uma foto para um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            })
    })
    FotoProdutoModel buscarFoto(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId) throws HttpMediaTypeNotAcceptableException ;

    @Operation(summary = "Busca uma foto para um produto de um restaurante", hidden = true)
    ResponseEntity<?> buscarArquivoFoto(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                        @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId,
                                        final String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Remove a foto de um produto do restaurante")
    void excluir(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                 @Parameter(description = "Id de um produto", example = "1", required = true) final Long produtoId);
    
}