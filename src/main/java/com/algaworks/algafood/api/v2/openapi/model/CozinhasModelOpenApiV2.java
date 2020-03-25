package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CozinhasModel")
@Data
public class CozinhasModelOpenApiV2 {

    public CidadeEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApiV2 page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi{
        private List<CozinhaModelV2> cozinhas;
    }

    
}