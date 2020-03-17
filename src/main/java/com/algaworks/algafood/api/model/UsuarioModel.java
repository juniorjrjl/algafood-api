package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel>{

    @ApiModelProperty(value = "ID do usuário", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "Nome do usuário", example = "Paula")
    private String nome;

    @ApiModelProperty(value = "E-mail do usuário", example = "paula@teste.com.br")
    private String email;
    
}