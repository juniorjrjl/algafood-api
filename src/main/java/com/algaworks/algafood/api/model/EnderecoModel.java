package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @ApiModelProperty(value = "CEP do endereço", example = "12030-999")
    private String cep;
    @ApiModelProperty(value = "logradouro do endereço", example = "Rua dos testes")
    private String logradouro;
    @ApiModelProperty(value = "número do endereço", example = "100")
    private String numero;
    @ApiModelProperty(value = "complemento do endereço", example = "apartamento 103")
    private String complemento;
    @ApiModelProperty(value = "bairro do endereço", example = "bairro dos testes")
    private String bairro;
    private CidadeResumoModel cidade;
    
}