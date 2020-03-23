package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.model.GrupoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    public GrupoEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GrupoEmbeddedModelOpenApi{
        private List<GrupoModel> grupos;
    }
    
}