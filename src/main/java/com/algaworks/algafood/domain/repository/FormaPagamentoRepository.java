package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import com.algaworks.algafood.domain.model.FormaPagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{
    
    @Query("select max(dataAtualizacao) from FormaPagamento")
    Optional<OffsetDateTime> getDataUltimaAtualizacao();

    @Query("select max(dataAtualizacao) from FormaPagamento where id = :id")
    Optional<OffsetDateTime> getDataUltimaAtualizacao(final Long id);

}