package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

    @ApiModelProperty(value = "Nome do produto", example = "Lasanha")
    @NotBlank
    private String nome;

    @ApiModelProperty(value = "Descrição do produto", example = "Lasanha de queijo")
    @NotBlank
    private String descricao;

    @ApiModelProperty(value = "Preço do produto", example = "10.00")
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(value = "Produto está ativo", example = "true")
    @NotNull
    private Boolean ativo;
    
}