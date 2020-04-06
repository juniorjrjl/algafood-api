package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@Component
public class AlgaSecurity {

	@Autowired
	private CadastroRestauranteService cadastrorestaurante;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId){
		return restauranteId == null ? 
				false : 
				cadastrorestaurante.existeResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean usuarioPodeGerenciarPedido(String codigoPedido) {
		return cadastroRestaurante.usuarioPodeGerenciarPedido(getUsuarioId(), codigoPedido);
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
	}
	
	public boolean isAutenticado() {
	    return getAuthentication().isAuthenticated();
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authorityName));
	}
	
	public boolean temEscopoEscrita() {
	    return hasAuthority("SCOPE_WRITE");
	}

	public boolean temEscopoLeitura() {
	    return hasAuthority("SCOPE_READ");
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return temEscopoEscrita() && (hasAuthority("GERENCIAR_PEDIDOS") || usuarioPodeGerenciarPedido(codigoPedido));
	}
	
	public boolean podeConsultarRestaurantes() {
	    return temEscopoLeitura() && isAutenticado();
	}

	public boolean podeGerenciarCadastroRestaurantes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}

	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
	    return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
	            || gerenciaRestaurante(restauranteId));
	}

	public boolean podeConsultarUsuariosGruposPermissoes() {
	    return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podeEditarUsuariosGruposPermissoes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
	    return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
	            || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}

	public boolean podePesquisarPedidos() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarFormasPagamento() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCidades() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstados() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCozinhas() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstatisticas() {
	    return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}    
	
}
