package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.UsuarioModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    public UsuarioEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public class UsuarioEmbeddedModelOpenApi{
        private List<UsuarioModel> usuarios;
    }
    
}