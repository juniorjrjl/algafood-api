package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService{

	private final UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		var usuario = usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return new User(usuario.getEmail(), usuario.getSenha(),getAuthorities(usuario));
	}

	private Collection<GrantedAuthority> getAuthorities(final Usuario usuario){
		return usuario.getGrupos().stream()
				.flatMap(g -> g.getPermissoes().stream())
				.map(p -> new SimpleGrantedAuthority(p.getNome().toUpperCase()))
				.collect(Collectors.toSet());
	}
	
}
