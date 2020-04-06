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
	
}
