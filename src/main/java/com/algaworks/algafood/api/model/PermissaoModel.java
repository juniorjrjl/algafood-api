package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModel {

    @ApiModelProperty(value = "ID da permissão", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "Nome do permissão", example = "1")
    private String nome;

    @ApiModelProperty(value = "Descrição do permissão", example = "Cadastrar Restaurante")
    private String descricao;
    
}