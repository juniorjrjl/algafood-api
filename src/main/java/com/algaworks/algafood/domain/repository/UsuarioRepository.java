package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import com.algaworks.algafood.domain.model.Usuario;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

    Optional<Usuario> findByIdAndSenha(final Long id, final String senha);

    Optional<Usuario> findByEmail(final String email);

}