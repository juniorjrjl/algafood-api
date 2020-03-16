package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {

    @ApiModelProperty(value = "ID do restaurante", example = "1")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

    @ApiModelProperty(value = "Nome do restaourante", example = "Thay Gourmet")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @ApiModelProperty(value = " Taxa de frete do restaurante", example = "10.00")
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    @ApiModelProperty(value = "Restaurante está ativo ", example = "true")
    private Boolean ativo;

    @ApiModelProperty(value = "Restaurante está aberto", example = "true")
    private Boolean aberto;

    private EnderecoModel endereco;
}