package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioAtualizacaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioCadastroInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi{

    private final UsuarioInputDisassembler usuarioInputDisassembler;

    private final UsuarioModelAssembler usuarioModelAssembler;

    private final CadastroUsuarioService cadastroUsuario;

    private final PasswordEncoder passwordEncoder;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> listar(){
        return usuarioModelAssembler.toCollectionModel(cadastroUsuario.listar());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("{idUsuario}")
    public UsuarioModel buscar(@PathVariable final Long idUsuario){
        return usuarioModelAssembler.toModel(cadastroUsuario.buscar(idUsuario));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid final UsuarioCadastroInput usuarioCadastroInput){
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioCadastroInput);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("{idUsuario}")
    public UsuarioModel atualizar(@PathVariable final Long idUsuario, @RequestBody @Valid final UsuarioAtualizacaoInput usuarioAtualizacaoInput){
        Usuario usuarioAtual = cadastroUsuario.buscar(idUsuario);
        usuarioInputDisassembler.copyToDomainInObject(usuarioAtualizacaoInput, usuarioAtual);
        usuarioAtual.setSenha(passwordEncoder.encode(usuarioAtual.getSenha()));
        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("{idUsuario}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable final Long idUsuario, @RequestBody @Valid final SenhaUsuarioInput senhaUsuarioInput){
        Usuario usuarioAtual = cadastroUsuario.buscar(idUsuario, senhaUsuarioInput.getSenhaAtual());
        usuarioInputDisassembler.copyToDomainInObject(senhaUsuarioInput, usuarioAtual);
        usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long idUsuario){
        cadastroUsuario.excluir(idUsuario);
    }

}