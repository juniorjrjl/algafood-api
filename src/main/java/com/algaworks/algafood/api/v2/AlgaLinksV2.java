package com.algaworks.algafood.api.v2;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinksV2 {

    public Link linkToCidade(final Long cidadeId){
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(CidadeControllerV2.class).buscar(cidadeId))
            .withSelfRel();
    }

    public Link linkToCidades(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(CidadeControllerV2.class)
            .listar()).withRel(relation);
    }

    public Link linkToCozinhas(final String relation){
        return WebMvcLinkBuilder
            .linkTo(CozinhaControllerV2.class).withRel(relation);
    }

}