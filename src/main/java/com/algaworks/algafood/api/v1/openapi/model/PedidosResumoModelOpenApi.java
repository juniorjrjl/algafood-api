package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.PedidoResumoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PedidosModel")
@Data
public class PedidosResumoModelOpenApi {

    public PedidoEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("PedidosEmbeddedModel")
    @Data
    public class PedidoEmbeddedModelOpenApi{
        private List<PedidoResumoModel> pedidos;
    }
    
}