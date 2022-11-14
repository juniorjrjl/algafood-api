package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class S3FotoStorageService implements FotoStorageService {

    private final AmazonS3 amazonS3;

    private final StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(final String nomeArquivo) {
        try{
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
            return FotoRecuperada.builder().url(url.toString()).build();
        }catch(Exception ex){
            throw new StorageException("Não foi possível recuperar o arquivo", ex);
        }
    }

    @Override
    public void armazenar(final NovaFoto novaFoto) {
        try{
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());
            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        }catch(Exception ex){
            throw new StorageException("Não foi possível enviar o arquivo para Amazon s3", ex);
        }
    }

    @Override
    public void remover(final String nomeArquivo) {
        try{
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(), 
                    caminhoArquivo);
            amazonS3.deleteObject(deleteObjectRequest);
        }catch(Exception ex){
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", ex);
        }

    }

    private String getCaminhoArquivo(final String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
    
}