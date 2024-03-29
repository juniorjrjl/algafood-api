package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
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
@RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi{

    private final CadastroUsuarioService cadastroUsuario;

    private final GrupoModelAssembler grupoModelAssembler;

	private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;
    
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable final Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
            .add(algaLinks.linkToGruposUsuarios(IanaLinkRelations.SELF.value(), usuarioId));
        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
        	gruposModel.add(algaLinks.linkToAssociarGrupo("associar", usuarioId));
        	gruposModel.forEach(g -> g.add(algaLinks.linkToDesassociarGrupo("dessassociar", usuarioId, g.getId())));
        }
        return gruposModel;
    }
    
    @PutMapping(value="/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarGrupo(@PathVariable final Long usuarioId,@PathVariable final Long grupoId) {
        cadastroUsuario.associarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarGrupo(@PathVariable final Long usuarioId,@PathVariable final Long grupoId) {
        cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
    
}