package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel>{

    @Autowired
    private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

    public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoModel);
		pedidoResumoModel.getCliente().add(algaLinks.linkToCliente(pedido.getCliente().getId()));
		pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		return pedidoResumoModel;
	}

	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> pedidos) {
		return super.toCollectionModel(pedidos);
	}
    
}