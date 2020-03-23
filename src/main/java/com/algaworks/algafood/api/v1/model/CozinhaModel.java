package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel>{

    @ApiModelProperty(value = "ID da cozinha", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "Nome da cozinha", example = "Japonesa")
    private String nome;
}