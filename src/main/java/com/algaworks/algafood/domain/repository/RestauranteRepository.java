package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, 
    RestauranteRepositoryQuery, 
    JpaSpecificationExecutor<Restaurante>{

        @Query("from Restaurante r join fetch r.cozinha")
        //@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
        List<Restaurante> findAll();

}