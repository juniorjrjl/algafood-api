package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

    FotoRecuperada recuperar(final String nomeArquivo);
    void armazenar(final NovaFoto novaFoto);
    void remover(final String nomeArquivo);

    default void substituir(final String nomeArquivoAntigo, final NovaFoto novaFoto){
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(final String nomeOriginal){
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }
    
    @Getter
    @Builder
    class NovaFoto{
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada{
        private InputStream inputStream;
        private String url;

        public boolean temURL(){
            return url != null;
        }

        public boolean temInputStream(){
            return inputStream != null;
        }

    }
}