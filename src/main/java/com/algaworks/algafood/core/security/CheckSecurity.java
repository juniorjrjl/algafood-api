package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	@interface Cozinhas {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
		@interface PodeEditar {}
		
	}
	
	@interface Restaurantes {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
		@interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
		@interface PodeGerenciarFuncionamento {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
		@interface PodeGerenciarCadastro {}
		
	}
	
	@interface Pedidos {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				     + "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
				     + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		@interface PodeBuscar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
		@interface PodeConsultar {}
		
		@PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface PodeGerenciarPedidos{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@interface PodeCriar {}
	}
	
	@interface FormaPagamentos {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
		@interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
		@interface PodeEditar {}
		
	}
	
	@interface Cidades {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarCidades()")
		@interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
		@interface PodeEditar {}
		
	}

	@interface Estados {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarEstados()")
		@interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
		@interface PodeEditar {}
		
	}
	
	@interface UsuariosGruposPermissoes {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(#idUsuario)")
		@interface PodeAlterarPropriaSenha {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.usuarioAutenticadoIgual(#idUsuario))")
		@interface PodeAlterarUsuario {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
		@interface PodeEditar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
		@interface PodeConsultar {}
		
	}
	
	@interface Estatisticas {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
		@interface PodeConsultar {}
		
	}
	
}
