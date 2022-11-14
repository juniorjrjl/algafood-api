package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento de um restaurante")
    CollectionModel<FormaPagamentoModel> listar(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId);

    @Operation(summary = "Associa uma forma de pagamento a um restaurante")
    ResponseEntity<Void> associarFormaPagamento(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                                @Parameter(description = "Id de uma forma de pagamento", example = "1", required = true) final Long formaPagamentoId);

    @Operation(summary = "Desassocia uma forma de pagamento a um restaurante")
    ResponseEntity<Void> desassociarFormaPagamento(@Parameter(description = "Id de um restaurante", example = "1", required = true) final Long restauranteId,
                                                   @Parameter(description = "Id de uma forma de pagamento", example = "1", required = true) final Long formaPagamentoId);
    
}