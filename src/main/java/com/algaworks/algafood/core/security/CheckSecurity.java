package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Cozinhas {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		public @interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
		public @interface PodeEditar {}
		
	}
	
	public @interface Restaurantes {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
		public @interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
		public @interface PodeGerenciarFuncionamento {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
		public @interface PodeGerenciarCadastro {}
		
	}
	
	public @interface Pedidos {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				     + "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
				     + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		public @interface PodeBuscar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
		public @interface PodeConsultar {}
		
		@PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedidos{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		public @interface PodeCriar {}
	}
	
	public @interface FormaPagamentos {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
		public @interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
		public @interface PodeEditar {}
		
	}
	
	public @interface Cidades {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarCidades()")
		public @interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
		public @interface PodeEditar {}
		
	}

	public @interface Estados {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarEstados()")
		public @interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
		public @interface PodeEditar {}
		
	}
	
	public @interface UsuariosGruposPermissoes {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(#idUsuario)")
		public @interface PodeAlterarPropriaSenha {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.usuarioAutenticadoIgual(#idUsuario))")
		public @interface PodeAlterarUsuario {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
		public @interface PodeEditar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
		public @interface PodeConsultar {}
		
	}
	
	public @interface Estatisticas {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
		public @interface PodeConsultar {}
		
	}
	
}
