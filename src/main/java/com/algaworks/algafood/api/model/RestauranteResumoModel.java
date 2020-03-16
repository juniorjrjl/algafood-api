package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoModel {

    @ApiModelProperty(value = "ID do cliente", example = "1")
    private Long id;
    @ApiModelProperty(value = "nome do cliente", example = "Paula")
    private String nome;
}