package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.core.validation.PasswordCheck;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordCheck(passwordField= "novaSenha", passwordConfirmationField = "confirmaNovaSenha")
public class SenhaUsuarioInput {

    @ApiModelProperty(value = "Senha atual do cliente", example = "123", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(value = "Confirmação da senha atual do cliente", 
                      example = "123", required = true)
    @NotBlank
    private String novaSenha;
    
    @ApiModelProperty(value = "Nova senha do cliente", example = "1234", required = true)
    @NotBlank
    private String confirmaNovaSenha;
    
}