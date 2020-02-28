package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    public Produto buscar(Long restauranteId, Long produtoId){
        return produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    public List<Produto> buscar(Long idRestaurante, boolean incluirInativos){
        Restaurante restaurante = cadastroRestaurante.buscar(idRestaurante);
        return (incluirInativos) ? 
            produtoRepository.findByRestaurante(restaurante) :
            produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Transactional
    public Produto salvar(Produto produto){
        Restaurante restaurante = cadastroRestaurante.buscar(produto.getRestaurante().getId());
        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long restauranteId, Long produtoId){
            if (produtoRepository.deleteByRestauranteIdAndId(restauranteId, produtoId) > 0){
                produtoRepository.flush();
            }else{
                throw new ProdutoNaoEncontradoException(restauranteId, produtoId);
            }
    }
}