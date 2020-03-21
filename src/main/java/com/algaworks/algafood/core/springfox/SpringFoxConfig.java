package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PedidosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

    @Bean
    public Docket apiDocket(){
		var typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors
                    .basePackage(RestauranteController.class.getPackageName()))
            .build()
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
			/*.globalOperationParameters(Arrays.asList(new ParameterBuilder()
				.name("campos") habilita documentação dos parametros do squiggly para todos os endpoints
				.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
				.parameterType("query")
				.modelRef(new ModelRef("string"))
				.build()))*/
			.additionalModels(typeResolver.resolve(Problem.class))
			.ignoredParameterTypes(ServletWebRequest.class, URL.class, 
								   URI.class, URLStreamHandler.class,
								   Resource.class, File.class, 
								   InputStream.class)
			.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, CozinhaModel.class), 
					CozinhasModelOpenApi.class))
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, PedidoModel.class), 
					PedidosModelOpenApi.class))
            .apiInfo((apiInfo()))
			.tags(new Tag("Cidades", "Gerencia as cidades"), 
				  new Tag("Grupos", "Gerencia os grupos"),
				  new Tag("Cozinhas", "Gerencia as cozinhas"),
				  new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
				  new Tag("Pedidos", "Gerencia os pedidos"),
				  new Tag("Restaurantes", "Gerencia os restaurantes"),
				  new Tag("Estados", "Gerencia os estados"),
				  new Tag("Produtos", "Gerencia os produtos"),
				  new Tag("Usuarios", "Gerencia os usuarios"),
				  new Tag("Permissoes", "Gerencia as Permissões"),
				  new Tag("Estatísticas", "Busca dados de vendas"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisição inválida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisição recusada porque o corpo está em um formato não suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisição inválida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().
            title("AlgaFood API").
            description("API aberta para clientes e restaurantes")
            .version("1")
            .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
            .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}