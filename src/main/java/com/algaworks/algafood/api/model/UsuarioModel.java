package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    @ApiModelProperty(value = "ID do cliente", example = "1")
    private Long id;
    @ApiModelProperty(value = "Nome do cliente", example = "Paula")
    private String nome;
    @ApiModelProperty(value = "E-mail do cliente", example = "paula@teste.com.br")
    private String email;
    
}