package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;


public interface ProdutoRepositoryQuery {

    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);
    
}