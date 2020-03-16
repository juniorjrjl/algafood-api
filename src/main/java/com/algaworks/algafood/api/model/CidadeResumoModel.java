package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome da cidade", example = "São Paulo")
    private String nome;
    
    @ApiModelProperty(value = "Estado da cidade", example = "São Paulo")
    private String estado;
}