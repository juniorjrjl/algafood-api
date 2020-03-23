package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInput {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    @NotNull
    private Long id;
    
}