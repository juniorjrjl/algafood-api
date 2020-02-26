package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery{

    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
    
    Long deleteByRestauranteIdAndId(Long restauranteId, Long produtoId);

    @Query("from Produto p where p.ativo = true and p.restaurante.id = :id")
    List<Produto> findAtivosByRestauranteId(Long id);

    List<Produto> findByRestauranteId(Long id);

    @Query("select f " + 
           "  from FotoProduto f "+
           "  join f.produto p " + 
           " where f.produto.id = :produtoId " + 
           "   and p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}