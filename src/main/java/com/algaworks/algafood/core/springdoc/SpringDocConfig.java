package com.algaworks.algafood.core.springdoc;

import com.algaworks.algafood.api.exceptionhandler.FieldErrorDetails;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.OAUTH2;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
@SecurityScheme(name = "security_auth", type = OAUTH2,
        flows = @OAuthFlows(authorizationCode =
            @OAuthFlow(authorizationUrl = "${springdoc.o-auth-flow.authorization-url}",
                    tokenUrl = "${springdoc.o-auth-flow.token-url}",
            scopes = {
                    @OAuthScope(name = "READ", description = "read scope"),
                    @OAuthScope(name = "WRITE", description = "write scope")
            })))
public class SpringDocConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public GroupedOpenApi groupedOpenApiV1(){
        return GroupedOpenApi.builder()
                .group("AlgaFood API V1")
                .pathsToMatch("/v1/**")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info().title("AlgaFood-API")
                                    .version("v1")
                                    .description("REST API do AlgaFood")
                                    .license(new License()
                                            .name("Apache 2.0")
                                            .url("http://springdoc.com")))
                            .externalDocs(new ExternalDocumentation()
                                    .description("AlgaWorks")
                                    .url("https://algaworks.com"));
                    incluirResponsesErro(openApi);
                    var components = openApi.getComponents();
                    gerarSchemas(components);
                    openApi.components(components.responses(gerarResponses()));
                })
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2(){
        return GroupedOpenApi.builder()
                .group("AlgaFood API V2")
                .pathsToMatch("/v2/**")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info().title("AlgaFood-API")
                                    .version("v2")
                                    .description("REST API do AlgaFood")
                                    .license(new License()
                                            .name("Apache 2.0")
                                            .url("http://springdoc.com")))
                            .externalDocs(new ExternalDocumentation()
                                    .description("AlgaWorks")
                                    .url("https://algaworks.com"));
                    incluirResponsesErro(openApi);
                    var components = openApi.getComponents();
                    gerarSchemas(components);
                    openApi.components(components.responses(gerarResponses()));
                })
                .build();
    }

    private void incluirResponsesErro(final OpenAPI openAPI){
        openAPI.getPaths()
                    .values()
                .forEach(p ->p.readOperationsMap()
                        .forEach((httpMethod, operation) -> {
                            var responses = operation.getResponses();
                            switch (httpMethod){
                                case GET -> {
                                    responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                    responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                }
                                case POST -> responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));

                                case PUT, PATCH -> {
                                    responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                    responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                }
                                case DELETE -> responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                            }
                            responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                        }));
    }

    private void gerarSchemas(final Components components) {
        ModelConverters.getInstance().read(Problem.class).forEach(components::addSchemas);
        ModelConverters.getInstance().read(FieldErrorDetails.class).forEach(components::addSchemas);
    }

    private Map<String, ApiResponse> gerarResponses() {
        Map<String, ApiResponse> apiResponseMap = new HashMap<>();
        var content = new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<Problem>().$ref("Problema")));
        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));
        return apiResponseMap;
    }

}
