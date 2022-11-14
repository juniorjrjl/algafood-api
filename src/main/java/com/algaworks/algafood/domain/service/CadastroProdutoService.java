package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CadastroProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CadastroRestauranteService cadastroRestaurante;

    public Produto buscar(final Long restauranteId, final Long produtoId){
        return produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    public List<Produto> buscar(final Long idRestaurante, final boolean incluirInativos){
        Restaurante restaurante = cadastroRestaurante.buscar(idRestaurante);
        return (incluirInativos) ? 
            produtoRepository.findByRestaurante(restaurante) :
            produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Transactional
    public Produto salvar(final Produto produto){
        Restaurante restaurante = cadastroRestaurante.buscar(produto.getRestaurante().getId());
        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(final Long restauranteId, final Long produtoId){
            if (produtoRepository.deleteByRestauranteIdAndId(restauranteId, produtoId) > 0){
                produtoRepository.flush();
            }else{
                throw new ProdutoNaoEncontradoException(restauranteId, produtoId);
            }
    }
}