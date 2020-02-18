package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Produto> buscar(Long id, boolean incluirInativos){
        return (incluirInativos) ? 
            produtoRepository.findByRestauranteId(id) :
            produtoRepository.findAtivosByRestauranteId(id);
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