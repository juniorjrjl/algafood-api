package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApiV2 {

    public CidadeEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi{
        private List<CidadeModelV2> cidades;
    }
    
}