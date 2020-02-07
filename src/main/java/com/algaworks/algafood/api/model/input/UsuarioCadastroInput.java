package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.core.validation.PasswordCheck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordCheck(passwordField= "senha", passwordConfirmationField = "confirmaSenha")
public class UsuarioCadastroInput {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String confirmaSenha;

}