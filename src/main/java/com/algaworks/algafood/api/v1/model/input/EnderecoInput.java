package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @Schema(example = "12030-000")
    @NotBlank
    private String cep;

    @Schema(example = "Rua Emílio Whinter")
    @NotBlank
    private String logradouro;

    @Schema(example = "134")
    @NotBlank
    private String numero;

    @Schema(example = "apartamento 45")
    private String complemento;

    @Schema(example = "Jardim das  Nações")
    @NotBlank
    private String bairro;
    
    @Valid
    @NotNull
    private CidadeIdInput cidade;
    
}