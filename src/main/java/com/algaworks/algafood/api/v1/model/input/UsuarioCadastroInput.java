package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.PasswordCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordCheck
public class UsuarioCadastroInput {

    @Schema(example = "João")
    @NotBlank
    private String nome;

    @Schema(example = "joao@joao.com")
    @NotBlank
    @Email
    private String email;

    @PasswordCheck.PasswordField
    @Schema(example = "123")
    @NotBlank
    private String senha;

    @PasswordCheck.PasswordConfirmationField
    @Schema(example = "123")
    @NotBlank
    private String confirmaSenha;

}