package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Cozinhas{
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		public @interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		public @interface PodeEditar {}
		
	}
	
	public @interface Restaurantes{
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		public @interface PodeConsultar{}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.gerenciaRestaurante(#idRestaurante))")
		public @interface PodeGerenciarFuncionamento {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
		public @interface PodeGerenciarCadastro {}
		
	}
	
	public @interface Pedidos{
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				     + "@algaSecurity.getUsuarioId() == returnObject.cliente.id or "
				     + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		public @interface PodeBuscar {}
		
	}
	
}
