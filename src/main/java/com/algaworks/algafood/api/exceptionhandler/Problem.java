package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Schema(name = "Problema")
public record Problem(
		@Schema(example = "400")
		@JsonProperty("status")
		Integer status,
		@Schema(example = "2022-07-15T01:01:50.902245498Z")
		@JsonProperty("timestamp")
		OffsetDateTime timestamp,
		@Schema(example = "https://algafood.com.br/dados-invalidos")
		@JsonProperty("type")
		String type,
		@Schema(example = "Dados inválidos")
		@JsonProperty("title")
		String title,
		@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto")
		@JsonProperty("detail")
		String detail,
		@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto")
		@JsonProperty("userMessage")
		String userMessage,
		@Schema(description = "lista de objetos ou campos que geraram erro")
		@JsonProperty("fields")
		List<FieldErrorDetails> fields){

	@Builder
	public Problem {
	}
}