package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel>{

    @ApiModelProperty(value = "ID do restaurante", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do restaourante", example = "Thay Gourmet")
    private String nome;

    @ApiModelProperty(value = " Taxa de frete do restaurante", example = "10.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

    @ApiModelProperty(value = "Restaurante está ativo ", example = "true")
    private Boolean ativo;

    @ApiModelProperty(value = "Restaurante está aberto", example = "true")
    private Boolean aberto;

    private EnderecoModel endereco;
}