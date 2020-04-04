package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioAtualizacaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioCadastroInput;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    public CollectionModel<UsuarioModel> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public UsuarioModel buscar(@ApiParam(value = "ID de um usuário", example = "1",required = true) 
                               Long idUsuario);

    @ApiOperation("Cadastra um Usuário")
    public UsuarioModel adicionar(UsuarioCadastroInput usuarioCadastroInput);

    @ApiOperation("Atualiza um Usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public UsuarioModel atualizar(@ApiParam(value = "ID de um usuário", example = "1",required = true)
                                  Long idUsuario, UsuarioAtualizacaoInput usuarioAtualizacaoInput);

    @ApiOperation("Atualiza a senha do usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public void alterarSenha(@ApiParam(value = "ID de um usuário", example = "1",required = true)
                             Long idUsuario, SenhaUsuarioInput senhaUsuarioInput);

    @ApiOperation("Exclui um Usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de um usuário", example = "1",required = true) Long idUsuario);
    
}