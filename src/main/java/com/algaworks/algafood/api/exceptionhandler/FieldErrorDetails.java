package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "ObjetoProblema")
public record FieldErrorDetails (
        @Schema(example = "preco")
        @JsonProperty("name")
        String name,
        @Schema(example = "o preço é inválido")
        @JsonProperty("userMessage")
        String userMessage){

    @Builder
    public FieldErrorDetails {
    }
}
