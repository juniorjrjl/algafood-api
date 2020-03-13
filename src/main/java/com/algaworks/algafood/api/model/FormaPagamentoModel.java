package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {

    @ApiModelProperty(value = "ID da forma de pagamento", example = "1")
    private Long id;
    @ApiModelProperty(value = "descrição da forma de pagamento", example = "Dinheiro")
    private String descricao;
}