package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteResponsavelControllerOpeApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

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
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteResponsavelControllerOpeApi{

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    
    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
        CollectionModel<UsuarioModel>  usuariosModel = usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
            .removeLinks()
            .add(algaLinks.linkToRestauranteResponsavel(IanaLinkRelations.SELF.value(), restauranteId))
            .add(algaLinks.linkToRestauranteResponsavelAssociar(IanaLinkRelations.SELF.value(), restauranteId));
        usuariosModel.forEach(u -> u.add(algaLinks.linkToRestauranteResponsavelDesassociar("desassociar", restauranteId, u.getId())));
        return usuariosModel;
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}