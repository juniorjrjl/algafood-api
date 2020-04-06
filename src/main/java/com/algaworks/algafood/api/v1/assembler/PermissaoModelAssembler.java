package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel>{

    @Autowired
	private ModelMapper modelMapper;

    @Autowired
    private AlgaSecurity algaSecurity;  
    
    @Autowired
	private AlgaLinks algaLinks;
    
	public PermissaoModelAssembler() {
		super(PermissaoController.class, PermissaoModel.class);
	}

    public PermissaoModel toModel(Permissao permissao) {
		PermissaoModel permissaoModel =  modelMapper.map(permissao, PermissaoModel.class);
		return permissaoModel;
	}

	@Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> permissoes) {
		CollectionModel<PermissaoModel> collectionModel = super.toCollectionModel(permissoes);

	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        collectionModel.add(algaLinks.linkToPermissoes(IanaLinkRelations.SELF.value()));
	    }
    
	    return collectionModel;            
	}
    
}