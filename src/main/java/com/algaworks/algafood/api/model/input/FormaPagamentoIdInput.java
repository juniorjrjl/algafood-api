package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {

    @ApiModelProperty(value = "ID da forma de pagamento", example = "1")
    @NotNull
    private Long id;
    
}