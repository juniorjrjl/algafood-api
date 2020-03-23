package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.EstadoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

    public EstadoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public class EstadoEmbeddedModelOpenApi{
        private List<EstadoModel> estados;
    }
    
}