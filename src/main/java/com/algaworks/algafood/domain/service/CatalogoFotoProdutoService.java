package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    public FotoProduto buscar(Long restauranteId, Long produtoId){
        return produtoRepository.findFotoById(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo){
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        Optional<FotoProduto> fotoAtual = produtoRepository.findFotoById(restauranteId, produtoId);
        String nomeArquivoExistente = null;
        if (fotoAtual.isPresent()){
            nomeArquivoExistente = fotoAtual.get().getNomeArquivo();
            produtoRepository.delete(fotoAtual.get());
        }
        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        foto = produtoRepository.save(foto);
        produtoRepository.flush();
        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .contentType(foto.getContentType())
            .inputStream(dadosArquivo)
            .build();
        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
        return foto;
    }
    
    @Transactional
    public void excluir(Long restauranteId, Long produtoId){
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);
        fotoStorageService.remover(fotoProduto.getNomeArquivo());
        produtoRepository.delete(fotoProduto);
    }

}