package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GrupoController implements GrupoControllerOpenApi{

    private final GrupoInputDisassembler grupoInputDisassembler;

    private final GrupoModelAssembler grupoModelAssembler;

    private final CadastroGrupoService cadastroGrupo;

	private final AlgaLinks algaLinks;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> listar(){
        return grupoModelAssembler.toCollectionModel(cadastroGrupo.listar())
            .add(algaLinks.linkToGrupos(IanaLinkRelations.SELF.value()));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("{idGrupo}")
    public GrupoModel buscar(@PathVariable final Long idGrupo){
        return grupoModelAssembler.toModel(cadastroGrupo.buscar(idGrupo));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid final GrupoInput grupoInput){
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("{idGrupo}")
    public GrupoModel atualizar(@PathVariable final Long idGrupo, @RequestBody @Valid final GrupoInput grupoInput){
        Grupo grupoAtual = cadastroGrupo.buscar(idGrupo);
        grupoInputDisassembler.copyToDomainInObject(grupoInput, grupoAtual);
        return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long idGrupo){
        cadastroGrupo.excluir(idGrupo);
    }

}