package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioAtualizacaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioCadastroInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuários", description = "Gerencia os usuários")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista usuários")
    CollectionModel<UsuarioModel> listar();

    @Operation(summary = "Busca usuário pelo id")
    UsuarioModel buscar(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long idUsuario);

    @Operation(summary = "Busca responsaveis por um restaurante")
    UsuarioModel adicionar(@RequestBody(description = "Representação de um novo usuário") final UsuarioCadastroInput usuarioCadastroInput);

    @Operation(summary = "Atualiza usuário pelo id")
    UsuarioModel atualizar(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long idUsuario,
                           @RequestBody(description = "Informações para atualizar o usuário") final UsuarioAtualizacaoInput usuarioAtualizacaoInput);

    @Operation(summary = "Altera s senha do usuário pelo id")
    void alterarSenha(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long idUsuario,
                      @RequestBody(description = "Informações para trocar a senha do usuário") final SenhaUsuarioInput senhaUsuarioInput);

    @Operation(summary = "Remove usuário pelo id")
    void remover(@Parameter(description = "Id de um usuário", example = "1", required = true) final Long idUsuario);
    
}