package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do produto", example = "Lasanha")
    private String nome;

    @ApiModelProperty(value = "Descrição do produto", example = "Lasanha quatro queijos")
    private String descricao;

    @ApiModelProperty(value = "Preço do produto", example = "10.00")
    private BigDecimal preco;

    @ApiModelProperty(value = "Produto está ativo", example = "1")
    private Boolean ativo;
    
}