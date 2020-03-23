package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.CozinhaModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CozinhasModel")
@Data
public class CozinhasModelOpenApi {

    public CidadeEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi{
        private List<CozinhaModel> cozinhas;
    }
    
}