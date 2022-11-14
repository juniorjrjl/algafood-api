package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CatalogoFotoProdutoService {

    private ProdutoRepository produtoRepository;

    private FotoStorageService fotoStorageService;

    public FotoProduto buscar(final Long restauranteId, final Long produtoId){
        return produtoRepository.findFotoById(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(final FotoProduto foto, final InputStream dadosArquivo){
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        Optional<FotoProduto> fotoAtual = produtoRepository.findFotoById(restauranteId, produtoId);
        String nomeArquivoExistente = null;
        if (fotoAtual.isPresent()){
            nomeArquivoExistente = fotoAtual.get().getNomeArquivo();
            produtoRepository.delete(fotoAtual.get());
        }
        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        var fotoSalva = produtoRepository.save(foto);
        produtoRepository.flush();
        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(fotoSalva.getNomeArquivo())
            .contentType(fotoSalva.getContentType())
            .inputStream(dadosArquivo)
            .build();
        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
        return fotoSalva;
    }
    
    @Transactional
    public void excluir(final Long restauranteId, Long produtoId){
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);
        fotoStorageService.remover(fotoProduto.getNomeArquivo());
        produtoRepository.delete(fotoProduto);
    }

}