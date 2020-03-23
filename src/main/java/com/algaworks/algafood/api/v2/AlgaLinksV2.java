package com.algaworks.algafood.api.v2;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinksV2 {

    public Link linkToCidade(Long cidadeId){
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(CidadeControllerV2.class).buscar(cidadeId))
            .withSelfRel();
    }

    public Link linkToCidades(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(CidadeControllerV2.class)
            .listar()).withRel(relation);
    }

}