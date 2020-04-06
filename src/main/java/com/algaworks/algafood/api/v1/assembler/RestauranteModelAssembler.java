package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

	@Autowired
	private AlgaSecurity algaSecurity; 
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

    public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
	        restauranteModel.add(algaLinks.linkToRestauranteProdutos("produtos", restaurante.getId()));
	    }
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(IanaLinkRelations.SELF.value(), restaurante.getCozinha().getId()));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null){
				restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento("formas-pagamento", restaurante.getId()));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteResponsaveis("responsaveis", restaurante.getId()));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(restaurante.getAtivo() ? 
				algaLinks.linkToInativarRestaurante("inativar", restaurante.getId()): 
				algaLinks.linkToAtivarRestaurante("ativar", restaurante.getId()));
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			restauranteModel.add(restaurante.getAberto() ? 
			algaLinks.linkToFecharRestaurante("fechar", restaurante.getId()): 
			algaLinks.linkToAbrirRestaurante("abrir", restaurante.getId()));
		}
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		return super.toCollectionModel(restaurantes).add(algaLinks.linkToRestaurantes(IanaLinkRelations.SELF.value()));
	}
    
}