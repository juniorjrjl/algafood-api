package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel>{

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Restaurante do Mineiro")
    private String nome;

    @Schema(example = "10.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

    @Schema(example = "true")
    private Boolean ativo;

    @Schema(example = "true")
    private Boolean aberto;

    private EnderecoModel endereco;
}