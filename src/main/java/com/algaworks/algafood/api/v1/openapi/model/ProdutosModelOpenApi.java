package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.ProdutoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    public ProdutoEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public class ProdutoEmbeddedModelOpenApi{
        private List<ProdutoModel> produtos;
    }
    
}