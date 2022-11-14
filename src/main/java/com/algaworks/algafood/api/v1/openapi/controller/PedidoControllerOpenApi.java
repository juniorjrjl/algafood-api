package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Busca pedidos de forma páginada")
    PagedModel<PedidoResumoModel> pesquisar(final PedidoFilter filtro, @Parameter(hidden = true) final Pageable pageable);

    @Operation(summary = "Busca pedido pelo id")
    PedidoModel buscar(@Parameter(description = "Id de um estado", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) final String codigoPedido);

    @Operation(summary = "Cadastra um pedido")
    PedidoModel emitir(@RequestBody(description = "Representação de um novo pedido") final PedidoInput pedidoInput);
    
}