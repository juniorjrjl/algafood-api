package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInput {

    @ApiModelProperty(value = "Nome do Estado",example = "São Paulo", required = true)
    @NotBlank
    private String nome;
}