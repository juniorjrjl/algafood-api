package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProdutoRepositoryQueryImpl implements ProdutoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return entityManager.merge(foto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto foto) {
        entityManager.remove(foto);

    }



    
}