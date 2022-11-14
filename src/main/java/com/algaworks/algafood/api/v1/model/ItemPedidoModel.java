package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    @Schema(example = "1")
    private Long produtoId;

    @Schema(example = "Brigadeiro Gourmet")
    private String nomeProduto;

    @Schema(example = "10")
    private int quantidade;

    @Schema(example = "18.90")
    private BigDecimal precoUnitario;

    @Schema(example = "189.00")
    private BigDecimal precoTotal;

    @Schema(example = "Embalagem premium")
    private String observacao;
    
}