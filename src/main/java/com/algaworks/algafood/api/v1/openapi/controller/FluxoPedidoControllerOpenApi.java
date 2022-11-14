package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface FluxoPedidoControllerOpenApi {

    @Operation(summary = "Marca o pedido como confirmado")
    ResponseEntity<Void> confirmar(@Parameter(description = "Id de um estado", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) final String codigoPedido);

    @Operation(summary = "Marca o pedido como entregue")
    ResponseEntity<Void> entregar(@Parameter(description = "Id de um estado", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) final String codigoPedido);

    @Operation(summary = "Marca o pedido como cancelado")
    ResponseEntity<Void> cancelar(@Parameter(description = "Id de um estado", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) final String codigoPedido);
    
}