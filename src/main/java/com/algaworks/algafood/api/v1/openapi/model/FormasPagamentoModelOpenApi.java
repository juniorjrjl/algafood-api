package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

    public FormaPagamentoEmbeddedModelOpenApi _embedded;
    
    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public class FormaPagamentoEmbeddedModelOpenApi{
        private List<FormaPagamentoModel> formasPagamento;
    }
    
}