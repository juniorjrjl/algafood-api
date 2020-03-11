package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormaPagamento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    private OffsetDateTime dataAtualizacao;

    @PrePersist
    private void preInsert(){
        this.dataAtualizacao = OffsetDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.dataAtualizacao = OffsetDateTime.now();
    }
}