package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioAtualizacaoInput {

    @Schema(example = "João")
    @NotBlank
    private String nome;

    @Schema(example = "joao@joao.com")
    @NotBlank
    @Email
    private String email;
    
}