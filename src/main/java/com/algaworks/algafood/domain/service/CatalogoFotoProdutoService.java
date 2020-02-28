package com.algaworks.algafood.domain.service;

import java.util.Optional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto){
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        Optional<FotoProduto> fotoAtual = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoAtual.isPresent()){
            produtoRepository.delete(fotoAtual.get());
        }
        return produtoRepository.save(foto);
    }
    
}