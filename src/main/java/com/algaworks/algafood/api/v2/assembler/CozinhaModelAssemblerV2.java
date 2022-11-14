package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2>{

    private final ModelMapper modelMapper;

	private final AlgaLinksV2 algaLinks;

	public CozinhaModelAssemblerV2(final ModelMapper modelMapper, final AlgaLinksV2 algaLinks) {
		super(CozinhaControllerV2.class, CozinhaModelV2.class);
		this.modelMapper = modelMapper;
		this.algaLinks = algaLinks;
	}

	@Override
    public CozinhaModelV2 toModel(final Cozinha cozinha) {
		CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
		modelMapper.map(cozinha, cozinhaModel);
		return cozinhaModel;
	}
    
}