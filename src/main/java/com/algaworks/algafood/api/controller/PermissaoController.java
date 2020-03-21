package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroPermissaoServive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi{

    @Autowired
    private CadastroPermissaoServive castroPermissao;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
	private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<PermissaoModel> listar(){
        List<Permissao> permissoes = castroPermissao.listar();
        return permissaoModelAssembler.toCollectionModel(permissoes)
            .add(algaLinks.linkToPermissoes(IanaLinkRelations.SELF.value()));
    }
    
}