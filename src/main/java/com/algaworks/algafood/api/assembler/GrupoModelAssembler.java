package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel>{

    @Autowired
    private ModelMapper modelMapper;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

    public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		grupoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
												.methodOn(GrupoController.class)
												.listar())
												.withRel("grupos"));
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
			.add(WebMvcLinkBuilder.linkTo(GrupoController.class).withSelfRel());
	}
    
}