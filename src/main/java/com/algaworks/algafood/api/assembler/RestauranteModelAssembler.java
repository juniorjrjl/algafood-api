package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel>{

    @Autowired
    private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

    public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(IanaLinkRelations.SELF.value(), restaurante.getCozinha().getId()));
		if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null){
			restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento("formas-pagamento", restaurante.getId()));
		restauranteModel.add(algaLinks.linkToRestauranteResponsaveis("responsaveis", restaurante.getId()));
		restauranteModel.add(restaurante.getAtivo() ? 
			algaLinks.linkToInativarRestaurante("inativar", restaurante.getId()): 
			algaLinks.linkToAtivarRestaurante("ativar", restaurante.getId()));
			restauranteModel.add(restaurante.getAberto() ? 
			algaLinks.linkToFecharRestaurante("fechar", restaurante.getId()): 
			algaLinks.linkToAbrirRestaurante("abrir", restaurante.getId()));
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		return super.toCollectionModel(restaurantes).add(algaLinks.linkToRestaurantes(IanaLinkRelations.SELF.value()));
	}
    
}