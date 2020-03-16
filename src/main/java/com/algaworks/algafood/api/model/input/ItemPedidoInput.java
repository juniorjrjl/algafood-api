package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @ApiModelProperty(value = "ID do produto do item", example = "1")
    @NotNull
    private Long produtoId;

    @ApiModelProperty(value = "quantidade de produto do item", example = "1")
    @Positive
    private Integer quantidade;
    
    @ApiModelProperty(value = "Observação do item do pedido", example = "1")
    private String observacao;
    
}