package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel>{

    @Autowired
	private ModelMapper modelMapper;

	public PermissaoModelAssembler() {
		super(PermissaoController.class, PermissaoModel.class);
	}

    public PermissaoModel toModel(Permissao permissao) {
		PermissaoModel permissaoModel =  modelMapper.map(permissao, PermissaoModel.class);
		return permissaoModel;
	}

	@Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> permissoes) {
		return super.toCollectionModel(permissoes);
	}
    
}