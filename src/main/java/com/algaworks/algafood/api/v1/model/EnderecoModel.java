package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @Schema(example = "12030-000")
    private String cep;

    @Schema(example = "Rua Emílio Whinter")
    private String logradouro;

    @Schema(example = "134")
    private String numero;

    @Schema(example = "apartamento 45")
    private String complemento;

    @Schema(example = "Jardim das  Nações")
    private String bairro;
    
    private CidadeResumoModel cidade;
    
}