package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel extends RepresentationModel<EstadoModel>{

    @ApiModelProperty(value = "ID de um Estado", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "Nome de um Estado", example = "São Paulo")
    private String nome;
}