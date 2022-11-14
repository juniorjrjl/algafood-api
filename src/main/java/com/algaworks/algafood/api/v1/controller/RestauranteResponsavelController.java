package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteResponsavelControllerOpeApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
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
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestauranteResponsavelController implements RestauranteResponsavelControllerOpeApi{

    private final CadastroRestauranteService cadastroRestaurante;

    private final UsuarioModelAssembler usuarioModelAssembler;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;
    
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable final Long restauranteId){
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
        CollectionModel<UsuarioModel>  usuariosModel = usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
            .removeLinks()
            .add(algaLinks.linkToRestauranteResponsavel(IanaLinkRelations.SELF.value(), restauranteId));
        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
        	usuariosModel.add(algaLinks.linkToRestauranteResponsavelAssociar("associar", restauranteId));
        	usuariosModel.forEach(u -> u.add(algaLinks.linkToRestauranteResponsavelDesassociar("desassociar", restauranteId, u.getId())));
        }
        return usuariosModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarUsuario(@PathVariable final Long restauranteId, @PathVariable final Long usuarioId){
        cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarUsuario(@PathVariable final Long restauranteId, @PathVariable final Long usuarioId){
        cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}