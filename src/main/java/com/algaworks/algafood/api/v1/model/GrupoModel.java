package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {

    @ApiModelProperty(value = "ID do grupo", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "Nome do grupo", example = "Usuário")
    private String nome;
}