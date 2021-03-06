package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long produtoId;

    @ApiModelProperty(value = "nome do produto", example = "Hamburger")
    private String nomeProduto;

    @ApiModelProperty(value = "quantidade de itens", example = "1")
    private int quantidade;

    @ApiModelProperty(value = "valor do produto do item", example = "10.00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(value = "valor do produto do item multiplicado pela quantidade", example = "10.00")
    private BigDecimal precoTotal;
    
    @ApiModelProperty(value = "observação do pedido", example = "sem cebola")
    private String observacao;
    
}