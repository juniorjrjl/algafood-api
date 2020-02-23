package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Restaurante_;

import org.springframework.data.jpa.domain.Specification;

public class RestauranteSpecs {

    public static Specification<Restaurante> freteGratis(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Restaurante_.taxaFrete), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> nomeSemelhante(String nome){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Restaurante_.nome), "%" + nome + "%");
    }
    
}