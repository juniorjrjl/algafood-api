package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries{

    @Query("Select p " +
           "  from Produto p" +
           " where p.restaurante.id = :restauranteId" +
           "   and p.id = :produtoId")
    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
    
    @Query("delete from Produto p where p.restaurante.id = :restauranteId and p.id = :produtoId")
    Long deleteByRestauranteIdAndId(Long restauranteId, Long produtoId);

    List<Produto> findByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);    

    @Query("select f " + 
           "  from FotoProduto f "+
           "  join f.produto p " + 
           " where f.produto.id = :produtoId " + 
           "   and p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

    @Transactional
    @Modifying
    @Query("delete from FotoProduto where id = :idFoto")
    void deleteFoto(Long idFoto);

    @Query("select count(f.id) > 0" + 
           "  from FotoProduto f" + 
           " where f.id = :fotoId")
    Boolean fotoExiste(Long fotoId);

}