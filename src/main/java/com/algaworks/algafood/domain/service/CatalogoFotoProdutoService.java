package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

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

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo){
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        Optional<FotoProduto> fotoAtual = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoAtual.isPresent()){
            produtoRepository.delete(fotoAtual.get());
        }
        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        foto = produtoRepository.save(foto);
        produtoRepository.flush();
        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dadosArquivo)
            .build();
        fotoStorageService.armazenar(novaFoto);
        return foto;
    }
    
}