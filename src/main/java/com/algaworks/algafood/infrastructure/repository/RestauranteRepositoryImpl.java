package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

import org.springframework.stereotype.Repository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;
    
}