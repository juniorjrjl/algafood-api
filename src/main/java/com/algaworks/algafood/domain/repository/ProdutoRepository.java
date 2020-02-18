package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.model.Produto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

    public Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
    
    public Long deleteByRestauranteIdAndId(Long restauranteId, Long produtoId);

    @Query("from Produto p where p.ativo = true and p.restaurante.id = :id")
    List<Produto> findAtivosByRestauranteId(Long id);

    List<Produto> findByRestauranteId(Long id);

}