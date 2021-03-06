package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel  extends RepresentationModel<PermissaoModel> {

    @ApiModelProperty(value = "Nome da foto", example = "Lasanha.png")
    private String nomeArquivo;

    @ApiModelProperty(value = "Descrição da foto", example = "Lasanha")
    private String descricao;

    @ApiModelProperty(value = "contentype da requisição", example = "application/json")
    private String contentType;

    @ApiModelProperty(value = "tamanho da foto em kb", example = "200")
    private Long tamanho;
    
}