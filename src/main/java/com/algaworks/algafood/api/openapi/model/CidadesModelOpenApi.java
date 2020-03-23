package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.model.CidadeModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

    public CidadeEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi{
        private List<CidadeModel> cidades;
    }
    
}