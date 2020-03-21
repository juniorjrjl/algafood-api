package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoServive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi{

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private CadastroPermissaoServive cadastroPermissao;

    @Autowired
	private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        CollectionModel<PermissaoModel> permissoesModel =  permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
        permissoesModel.add(algaLinks.linkToAssociarPermissao("associar", grupoId));
        permissoesModel.add(algaLinks.linkToGrupoPermissoes(IanaLinkRelations.SELF.value(), grupoId));
        permissoesModel.forEach(p -> p.add(algaLinks.linkToDesassociarPermissao("desassociar", grupoId, p.getId())));
        return permissoesModel;
    }
    
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  associar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        cadastroPermissao.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        cadastroPermissao.dessassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

}