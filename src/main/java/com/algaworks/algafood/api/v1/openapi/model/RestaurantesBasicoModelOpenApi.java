package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    public RestauranteBasicoEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("RestaurantesEmbeddedModel")
    @Data
    public class RestauranteBasicoEmbeddedModelOpenApi{
        private List<RestauranteBasicoModel> restaurantes;
    }
    
}