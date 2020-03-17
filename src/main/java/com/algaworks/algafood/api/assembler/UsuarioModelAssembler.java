package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel>{

    @Autowired
    private ModelMapper modelMapper;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

    public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		usuarioModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
												.methodOn(UsuarioController.class)
												.listar())
												.withRel("usuarios"));
		usuarioModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
												.methodOn(UsuarioGrupoController.class)
												.listar(usuario.getId()))
												.withRel("grupos-usuarios"));
		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> usuarios) {
		return super.toCollectionModel(usuarios)
			.add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
	}
    
}