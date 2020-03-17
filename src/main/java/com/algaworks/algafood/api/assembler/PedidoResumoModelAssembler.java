package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel>{

    @Autowired
    private ModelMapper modelMapper;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

    public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoModel);
		pedidoResumoModel.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(UsuarioController.class).buscar(pedido.getCliente().getId()))
			.withSelfRel());
		pedidoResumoModel.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId()))
			.withSelfRel());
		return pedidoResumoModel;
	}

	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> pedidos) {
		return super.toCollectionModel(pedidos);
	}
    
}