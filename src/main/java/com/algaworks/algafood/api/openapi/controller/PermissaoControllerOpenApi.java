package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PermissaoModel;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissoes")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissoes")
    public CollectionModel<PermissaoModel> listar();
    
}