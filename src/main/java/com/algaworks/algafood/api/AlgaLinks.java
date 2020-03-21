package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FluxoPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.controller.RestauranteResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinks {

    public static final TemplateVariables pageVariables = new TemplateVariables(
        new TemplateVariable("page", VariableType.REQUEST_PARAM),
        new TemplateVariable("size", VariableType.REQUEST_PARAM),
        new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public Link linkToPedidos(String relation){
		TemplateVariables filterVariable = new TemplateVariables(
			new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
			new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		return new Link(UriTemplate.of(pedidosUrl, pageVariables.concat(filterVariable)), relation);
    }
    
    public Link linkToRestaurante(Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(RestauranteController.class).buscar(restauranteId))
			.withSelfRel();
    }

    public Link linkToCliente(Long clienteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioController.class).buscar(clienteId))
            .withSelfRel();
    }

    public Link linkToFormaPagamento(String relation, Long formaPagamentoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null))
            .withRel(relation);
    }

    public Link linkToFormasPagamento(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(FormaPagamentoController.class).listar(null))
            .withRel(relation);
    }

    public Link linkToCidade(Long cidadeId){
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(CidadeController.class).buscar(cidadeId))
            .withSelfRel();
    }

    public Link linkToProduto(Long restauranteId, Long produtoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId))
            .withRel("produto");
    }

    public Link linkToCidades(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(CidadeController.class)
            .listar()).withRel(relation);
    }

    public Link linkToEstado(Long estadoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(EstadoController.class)
            .buscar(estadoId))
            .withSelfRel();
    }

    public Link linkToEstados(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(EstadoController.class)
            .listar()).withRel(relation);
    }

    public Link linkToCozinhas(){
        return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel("cozinhas");
    }

    public Link linkToUsuarios(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
		    .methodOn(UsuarioController.class)
            .listar()).withRel(relation);
    }

    public Link linkToGruposUsuarios(Long usuarioId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioGrupoController.class)
            .listar(usuarioId))
            .withRel("grupos-usuarios");
    }

    public Link linkToRestauranteResponsavel(String relation, Long restauranteId){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(RestauranteResponsavelController.class)
            .listar(restauranteId))
            .withRel(relation);
    }

    public Link linkToConfirmacaoPedido(String relation, String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .confirmar(codigoPedido))
            .withRel(relation);
    }

    public Link linkToEntregaPedido(String relation, String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .entregar(codigoPedido)).withRel(relation);
    }

    public Link linkToCancelamentoPedido(String relation, String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .cancelar(codigoPedido)).withRel(relation);
    }

    public Link linkToCozinha(String relation, Long cozinhaId){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
            .buscar(cozinhaId)).withRel(relation);
    }

    public Link linkToRestaurantes(String relation){
        TemplateVariables params = new TemplateVariables(new TemplateVariable("projecao", VariableType.REQUEST_PARAM));    
        String pedidosUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
        return new Link(UriTemplate.of(pedidosUrl, params), relation);
    }

    public Link linkToRestauranteFormasPagamento(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteResponsaveis(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .listar(restauranteId)).withRel(relation);
    }

    public Link linkToAbrirRestaurante(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .abrir(restauranteId)).withRel(relation);
    }

    public Link linkToFecharRestaurante(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .fechar(restauranteId)).withRel(relation);
    }

    public Link linkToAtivarRestaurante(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .ativar(restauranteId)).withRel(relation);
    }

    public Link linkToInativarRestaurante(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .inativar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(String relation, Long restauranteId, Long formaPagamentoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .desassociarFormaPagamento(restauranteId, formaPagamentoId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .associarFormaPagamento(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelAssociar(String relation, Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .associarUsuario(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelDesassociar(String relation, Long restauranteId, Long usuarioId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .desassociarUsuario(restauranteId, usuarioId)).withRel(relation);
    }

    public Link linkToRestauranteProdutos(String relation, Long restauranteId){
		TemplateVariables filterVariable = new TemplateVariables(
			new TemplateVariable("incluirInativos", VariableType.REQUEST_PARAM));
        String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                                    .methodOn(RestauranteProdutoController.class)
                                            .listar(restauranteId, null)).toUri().toString();
		return new Link(UriTemplate.of(pedidosUrl, filterVariable), relation);
    }

    public Link linkToFotoProduto(String relation, Long restauranteId, Long produtoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteProdutoFotoController.class)
            .buscarFoto(restauranteId, produtoId)).withRel(relation);
    }

    public Link linkToGrupos(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoController.class)
			.listar())
			.withRel(relation);
    }

    public Link linkToGrupoPermissoes(String relation, Long grupoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.listar(grupoId))
			.withRel(relation);
    }

    public Link linkToPermissoes(String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(PermissaoController.class)
			.listar())
			.withRel(relation);
    }

    public Link linkToDesassociarPermissao(String relation, Long grupoId, Long permissaoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.desassociar(grupoId, permissaoId))
			.withRel(relation);
    }

    public Link linkToAssociarPermissao(String relation, Long grupoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.associar(grupoId, null))
			.withRel(relation);
    }

}