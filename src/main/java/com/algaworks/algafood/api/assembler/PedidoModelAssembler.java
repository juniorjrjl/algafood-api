package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

    @Autowired
    private ModelMapper modelMapper;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

    public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		pedidoModel.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId()))
			.withSelfRel());
		pedidoModel.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(UsuarioController.class).buscar(pedido.getCliente().getId()))
			.withSelfRel());
		pedidoModel.getFormaPagamento().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(FormaPagamentoController.class).buscar(pedido.getFormaPagamento().getId(), null))
			.withSelfRel());
		pedidoModel.getEnderecoEntrega().getCidade().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(CidadeController.class).buscar(pedido.getEnderecoEntrega().getCidade().getId()))
			.withSelfRel());
		pedidoModel.getItens().forEach(i -> i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(RestauranteProdutoController.class).buscar(pedido.getRestaurante().getId(), 
																	i.getProdutoId()))
			.withRel("produto")));
		pedidoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(PedidoController.class).pesquisar(null, null)).withRel("pedidos"));
		return pedidoModel;
	}
 
	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> pedidos) {
		return super.toCollectionModel(pedidos);
	}
	
}