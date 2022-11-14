package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Estados", description = "Gerencia os estados")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Lista os estados")
    CollectionModel<EstadoModel> listar();

    @Operation(summary = "Busca um estado pelo id")
    EstadoModel buscar(@Parameter(description = "Id de um estado", example = "1", required = true) final Long idEstado);

    @Operation(summary = "Cadastra um estado")
    EstadoModel adicionar(@RequestBody(description = "Representação de um novo estado") final EstadoInput estadoInput);

    @Operation(summary = "Atualiza um estado pelo id")
    EstadoModel atualizar(@Parameter(description = "Id de um estado", example = "1", required = true) final Long idEstado,
                          @RequestBody(description = "Informações para atualizar o estado")final EstadoInput estadoInput);

    @Operation(summary = "Remove um estado pelo id")
    void remover(@Parameter(description = "Id de um estado", example = "1", required = true) final Long idEstado);
    
}