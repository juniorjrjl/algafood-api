package com.algaworks.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager){
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

	@Override
	public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();
        T entity = entityManager.createQuery(jpql, getDomainClass())
            .setMaxResults(1)
            .getSingleResult();
		return Optional.ofNullable(entity);
	}

    @Override
    public void detach(final T entity) {
        entityManager.detach(entity);
    }

    
}