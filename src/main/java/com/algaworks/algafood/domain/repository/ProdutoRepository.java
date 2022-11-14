package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries{

    @Query("Select p " +
           "  from Produto p" +
           " where p.restaurante.id = :restauranteId" +
           "   and p.id = :produtoId")
    Optional<Produto> findByRestauranteIdAndId(final Long restauranteId, final Long produtoId);
    
    @Query("delete from Produto p where p.restaurante.id = :restauranteId and p.id = :produtoId")
    Long deleteByRestauranteIdAndId(final Long restauranteId, final Long produtoId);

    List<Produto> findByRestaurante(final Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(final Restaurante restaurante);

    @Query("select f " + 
           "  from FotoProduto f "+
           "  join f.produto p " + 
           " where f.produto.id = :produtoId " + 
           "   and p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoById(final Long restauranteId, final Long produtoId);

}