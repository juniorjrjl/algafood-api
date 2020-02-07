package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioAtualizacaoInput;
import com.algaworks.algafood.api.model.input.UsuarioCadastroInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @GetMapping
    public List<UsuarioModel> listar(){
        return usuarioModelAssembler.toCollectionModel(cadastroUsuario.listar());
    }

    @GetMapping("{id}")
    public UsuarioModel buscar(@PathVariable Long id){
        return usuarioModelAssembler.toModel(cadastroUsuario.buscar(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioCadastroInput usuarioCadastroInput){
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioCadastroInput);
        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
    }

    @PutMapping("{id}")
    public UsuarioModel atualizar(@PathVariable Long id, 
        @RequestBody @Valid UsuarioAtualizacaoInput usuarioAtualizacaoInput){
        Usuario usuarioAtual = cadastroUsuario.buscar(id);
        usuarioInputDisassembler.copyToDomainInObject(usuarioAtualizacaoInput, usuarioAtual);
        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }
    
    @PutMapping("{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaUsuarioInput senhaUsuarioInput){
        Usuario usuarioAtual = cadastroUsuario.buscar(id, senhaUsuarioInput.getSenhaAtual());
        usuarioInputDisassembler.copyToDomainInObject(senhaUsuarioInput, usuarioAtual);
        usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroUsuario.excluir(id);
    }

}