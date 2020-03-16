package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput {

    @ApiModelProperty(value = "ID da cozinha", example = "1", required = true)
    @NotNull
    private Long id;
    
}