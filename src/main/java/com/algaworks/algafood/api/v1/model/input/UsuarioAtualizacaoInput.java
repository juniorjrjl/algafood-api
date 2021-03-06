package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizacaoInput {

    @ApiModelProperty(value = "Nome do usuário", example = "Paula", required = true)
    @NotBlank
    private String nome;
    
    @ApiModelProperty(value = "E-mail do usuário", example = "paula@teste.com.br", required = true)
    @NotBlank
    @Email
    private String email;
    
}