package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.core.validation.PasswordCheck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordCheck(passwordField= "novaSenha", passwordConfirmationField = "confirmaNovaSenha")
public class SenhaUsuarioInput {

    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
    @NotBlank
    private String confirmaNovaSenha;
    
}