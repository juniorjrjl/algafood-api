package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.TipoStorage;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class StorageConfig {


    private final StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(prefix = "algafood.storage", name = "tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
            storageProperties.getS3().getIdChaveAcesso(), 
            storageProperties.getS3().getChaveAcessoSecreta());
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(storageProperties.getS3().getRegiao())
            .build();
    }
    
    @Bean
    public FotoStorageService fotoStorageService(final Optional<AmazonS3> amazonS3, final StorageProperties storageProperties){
        return (amazonS3.isPresent()) ?
                new S3FotoStorageService(amazonS3.get(), storageProperties) :
                new LocalFotoStorageService(storageProperties);
    }

}