package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(value = "Cep do endereço", example = "12030-999", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(value = "Lograurodo endereço", example = "Rua dos testes", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(value = "Número do endereço", example = "1", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(value = "Complemento do endereço", example = "apartamento 103", required = true)
    private String complemento;
    
    @ApiModelProperty(value = "Bairro do endereço", example = "bairro dos testes", required = true)
    @NotBlank
    private String bairro;
    
    @Valid
    @NotNull
    private CidadeIdInput cidade;
    
}