package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2>{

    @ApiModelProperty(value = "ID da cozinha", example = "1")
    private Long idCozinha;
    
    @ApiModelProperty(value = "Nome da cozinha", example = "Japonesa")
    private String nomeCozinha;
}