package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Parameter(
        in = QUERY,
        name = "page",
        description = "Número de página (0..N)",
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        in = QUERY,
        name = "size",
        description = "Quantidade de elementos por página",
        schema = @Schema(type = "integer", defaultValue = "10")
)
@Parameter(
        in = QUERY,
        name = "sort",
        description = "Critério de ordenação: propriedade(asc|desc)",
        examples = {
                @ExampleObject("nome"),
                @ExampleObject("nome,asc"),
                @ExampleObject("nome,desc")
        }
)
public @interface PageableParameter {

}
