package com.algaworks.algafood.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputV2 {

    @Schema(example = "Brasileira")
    @NotBlank
    private String nomeCozinha;
}