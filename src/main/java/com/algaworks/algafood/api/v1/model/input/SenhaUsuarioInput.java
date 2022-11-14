package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.PasswordCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordCheck
public class SenhaUsuarioInput {


    @Schema(example = "1234")
    @NotBlank
    private String senhaAtual;

    @PasswordCheck.PasswordField
    @Schema(example = "123")
    @NotBlank
    private String novaSenha;

    @PasswordCheck.PasswordConfirmationField
    @Schema(example = "123")
    @NotBlank
    private String confirmaNovaSenha;
    
}