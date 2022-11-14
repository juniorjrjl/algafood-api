package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoServive;
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
@RequestMapping(path = "/v1grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi{

    private final PermissaoModelAssembler permissaoModelAssembler;

    private final CadastroGrupoService cadastroGrupo;

    private final CadastroPermissaoServive cadastroPermissao;

	private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable final Long grupoId) {
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        CollectionModel<PermissaoModel> permissoesModel =  permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
        permissoesModel.add(algaLinks.linkToGrupoPermissoes(IanaLinkRelations.SELF.value(), grupoId));
        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
	        permissoesModel.add(algaLinks.linkToAssociarPermissao("associar", grupoId));
	        permissoesModel.forEach(p -> p.add(algaLinks.linkToDesassociarPermissao("desassociar", grupoId, p.getId())));
        }
        return permissoesModel;
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  associar(@PathVariable final Long grupoId, @PathVariable final Long permissaoId){
        cadastroPermissao.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  desassociar(@PathVariable final Long grupoId, @PathVariable final Long permissaoId){
        cadastroPermissao.dessassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

}