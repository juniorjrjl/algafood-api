package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Formas de pagamentos", description = "Gerencia as formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(final ServletWebRequest request);

    @Operation(summary = "Busca uma forma de pagamento pelo id")
    ResponseEntity<FormaPagamentoModel> buscar(@Parameter(description = "Id de uma forma de pagamento", example = "1", required = true) final Long idFormaPagamento,
                                               final ServletWebRequest request);

    @Operation(summary = "Cadastra uma forma de pagamento")
    FormaPagamentoModel adicionar(@RequestBody(description = "Representação de uma nova forma de pagamento") final FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Atualiza uma forma de pagamento pelo id")
    FormaPagamentoModel atualizar(@Parameter(description = "Id de uma forma de pagamento", example = "1", required = true) final Long idFormaPagamento,
                                  @RequestBody(description = "Informações para atualizar a forma de pagamento") final FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Exclui uma forma de pagamento pelo id")
    void remover(@Parameter(description = "Id de uma forma de pagamento", example = "1", required = true) final Long idFormaPagamento);
    
}