package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.core.validation.PasswordCheck;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordCheck(passwordField= "senha", passwordConfirmationField = "confirmaSenha")
public class UsuarioCadastroInput {

    @ApiModelProperty(value = "Nome do usuário", example = "Paula", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(value = "E-mail do usuário", example = "paula@teste.com.br", required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "Senha do usuário", example = "123", required = true)
    @NotBlank
    private String senha;
    
    @ApiModelProperty(value = "Confirmação da senha do usuário", example = "123", required = true)
    @NotBlank
    private String confirmaSenha;

}