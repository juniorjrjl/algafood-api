package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Pizza de Frango com Caputiry")
    private String nome;

    @Schema(example = "uma deliciosa pizza feita em forno elétrico")
    private String descricao;

    @Schema(example = "18.90")
    private BigDecimal preco;

    @Schema(example = "true")
    private Boolean ativo;
    
}