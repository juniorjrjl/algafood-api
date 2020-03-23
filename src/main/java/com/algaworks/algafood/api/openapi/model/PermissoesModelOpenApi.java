package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.model.PermissaoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

    public PermissaoEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public class PermissaoEmbeddedModelOpenApi{
        private List<PermissaoModel> permissoes;
    }
    
}