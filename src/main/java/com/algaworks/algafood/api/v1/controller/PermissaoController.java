package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
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
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
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