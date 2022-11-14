package com.algaworks.algafood.api.v1;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;

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

    public Link linkToPedidos(final String relation){
		TemplateVariables filterVariable = new TemplateVariables(
			new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
			new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl, pageVariables.concat(filterVariable)), relation);
    }
    
    public Link linkToRestaurante(final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(RestauranteController.class).buscar(restauranteId))
			.withSelfRel();
    }

    public Link linkToCliente(final Long clienteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioController.class).buscar(clienteId))
            .withSelfRel();
    }

    public Link linkToFormaPagamento(final String relation, Long formaPagamentoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null))
            .withRel(relation);
    }

    public Link linkToFormasPagamento(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(FormaPagamentoController.class).listar(null))
            .withRel(relation);
    }

    public Link linkToCidade(final Long cidadeId){
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(CidadeController.class).buscar(cidadeId))
            .withSelfRel();
    }

    public Link linkToProduto(final Long restauranteId, final Long produtoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId))
            .withRel("produto");
    }

    public Link linkToCidades(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(CidadeController.class)
            .listar()).withRel(relation);
    }

    public Link linkToEstado(final Long estadoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(EstadoController.class)
            .buscar(estadoId))
            .withSelfRel();
    }

    public Link linkToEstados(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
			.methodOn(EstadoController.class)
            .listar()).withRel(relation);
    }

    public Link linkToCozinhas(final String relation){
        return WebMvcLinkBuilder
            .linkTo(CozinhaController.class).withRel(relation);
    }

    public Link linkToUsuarios(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
		    .methodOn(UsuarioController.class)
            .listar()).withRel(relation);
    }

    public Link linkToGruposUsuarios(final String relation, final Long usuarioId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioGrupoController.class)
            .listar(usuarioId))
            .withRel(relation);
    }

    public Link linkToRestauranteResponsavel(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(RestauranteResponsavelController.class)
            .listar(restauranteId))
            .withRel(relation);
    }

    public Link linkToConfirmacaoPedido(final String relation, final String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .confirmar(codigoPedido))
            .withRel(relation);
    }

    public Link linkToEntregaPedido(final String relation, final String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .entregar(codigoPedido)).withRel(relation);
    }

    public Link linkToCancelamentoPedido(final String relation, final String codigoPedido){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
            .cancelar(codigoPedido)).withRel(relation);
    }

    public Link linkToCozinha(final String relation, final Long cozinhaId){
        return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
            .buscar(cozinhaId)).withRel(relation);
    }

    public Link linkToRestaurantes(final String relation){
        TemplateVariables params = new TemplateVariables(new TemplateVariable("projecao", VariableType.REQUEST_PARAM));    
        String pedidosUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, params), relation);
    }

    public Link linkToRestauranteFormasPagamento(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteResponsaveis(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .listar(restauranteId)).withRel(relation);
    }

    public Link linkToAbrirRestaurante(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .abrir(restauranteId)).withRel(relation);
    }

    public Link linkToFecharRestaurante(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .fechar(restauranteId)).withRel(relation);
    }

    public Link linkToAtivarRestaurante(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .ativar(restauranteId)).withRel(relation);
    }

    public Link linkToInativarRestaurante(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteController.class)
            .inativar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(final String relation, final Long restauranteId, final Long formaPagamentoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .desassociarFormaPagamento(restauranteId, formaPagamentoId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteFormaPagamentoController.class)
            .associarFormaPagamento(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelAssociar(final String relation, final Long restauranteId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .associarUsuario(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelDesassociar(final String relation, final Long restauranteId, final Long usuarioId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteResponsavelController.class)
            .desassociarUsuario(restauranteId, usuarioId)).withRel(relation);
    }

    public Link linkToRestauranteProdutos(final String relation, final Long restauranteId){
		TemplateVariables filterVariable = new TemplateVariables(
			new TemplateVariable("incluirInativos", VariableType.REQUEST_PARAM));
        String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                                    .methodOn(RestauranteProdutoController.class)
                                            .listar(restauranteId, null)).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl, filterVariable), relation);
    }

    public Link linkToFotoProduto(final String relation, final Long restauranteId, final Long produtoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(RestauranteProdutoFotoController.class)
            .buscarFoto(restauranteId, produtoId)).withRel(relation);
    }

    public Link linkToGrupos(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoController.class)
			.listar())
			.withRel(relation);
    }

    public Link linkToGrupoPermissoes(final String relation, final Long grupoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.listar(grupoId))
			.withRel(relation);
    }

    public Link linkToPermissoes(final String relation){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(PermissaoController.class)
			.listar())
			.withRel(relation);
    }

    public Link linkToDesassociarPermissao(final String relation, final Long grupoId, final Long permissaoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.desassociar(grupoId, permissaoId))
			.withRel(relation);
    }

    public Link linkToAssociarPermissao(final String relation, final Long grupoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(GrupoPermissaoController.class)
			.associar(grupoId, null))
			.withRel(relation);
    }

    public Link linkToDesassociarGrupo(final String relation, final Long usuarioId, final Long grupoId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioGrupoController.class)
			.desassociarGrupo(usuarioId, grupoId))
			.withRel(relation);
    }

    public Link linkToAssociarGrupo(final String relation, final Long usuarioId){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioGrupoController.class)
			.associarGrupo(usuarioId, null))
			.withRel(relation);
    }

    public Link linkToEstatisticas(final String rel) {
        return WebMvcLinkBuilder.
            linkTo(EstatisticasController.class).withRel(rel);
    }
    
    public Link linkToEstatisticasVendasDiarias(final String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
        
        String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
            .methodOn(EstatisticasController.class)
            .consultar(null, null)).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), rel);
    } 

}