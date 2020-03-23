package com.algaworks.algafood.api.v1.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.v1.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RestauranteBasicoModel")
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(value = "ID do restaurante", example = "1")
    private Long id;
    @ApiModelProperty(value = "Nome do restaurante", example = "Thai Gourmet")
    private String nome;
    @ApiModelProperty(value = "Taxa de frete do restaurante", example = "10.00")
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;    
}