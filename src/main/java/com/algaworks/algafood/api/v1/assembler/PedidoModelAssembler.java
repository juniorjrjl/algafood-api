package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

    @Autowired
    private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

    public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		pedidoModel.getCliente().add(algaLinks.linkToCliente(pedido.getCliente().getId()));
		pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(IanaLinkRelations.SELF.value(), pedido.getFormaPagamento().getId()));
		pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));	
		pedidoModel.getItens().forEach(i -> i.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), i.getProdutoId())));
		pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		if (pedido.podeConfirmar()){
			pedidoModel.add(algaLinks.linkToConfirmacaoPedido("confirmar", pedido.getCodigo()));
		}
		if (pedido.podeCancelar()){
			pedidoModel.add(algaLinks.linkToCancelamentoPedido("cancelar", pedido.getCodigo()));
		}
		if (pedido.podeEntregar()){
			pedidoModel.add(algaLinks.linkToEntregaPedido("entregar", pedido.getCodigo()));
		}
		return pedidoModel;
	}
 
	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> pedidos) {
		return super.toCollectionModel(pedidos);
	}
	
}