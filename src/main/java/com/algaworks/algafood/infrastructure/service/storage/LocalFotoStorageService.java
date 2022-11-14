package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

@AllArgsConstructor
public class LocalFotoStorageService implements FotoStorageService {

    private final StorageProperties storageProperties;

    @Override
    public void armazenar(final NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possivel armazenar o arquivo", e);
        }
    }

    @Override
    public void remover(final String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possivel excluir o arquivo", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(final String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return FotoRecuperada.builder()
                .inputStream(Files.newInputStream(arquivoPath))
                .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possivel recuperar o arquivo", e);
        }
    }

    private Path getArquivoPath(final String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
    
}