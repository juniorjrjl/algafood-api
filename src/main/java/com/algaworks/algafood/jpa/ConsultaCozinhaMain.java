package com.algaworks.algafood.jpa;

import java.util.List;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCozinhaMain {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japonesa");

        cozinhaRepository.salvar(cozinha1);
        cozinhaRepository.salvar(cozinha2);

        List<Cozinha> cozinhas = cozinhaRepository.listar();
        cozinhas.forEach(c -> System.out.println(c.getNome()));

        Cozinha cozinhaRecuperada = cozinhaRepository.buscar(3L);
        System.out.printf("cozinha recuperada : %s - %s", cozinhaRecuperada.getId(), cozinhaRecuperada.getNome());

        Cozinha cozinhaAtualizar = new Cozinha();
        cozinhaAtualizar.setId(1L);
        cozinhaAtualizar.setNome("Italiana");
        Cozinha cozinhaAtualizada = cozinhaRepository.salvar(cozinhaAtualizar);
        System.out.printf("cozinha atualizada %s - %s: ", cozinhaAtualizada.getId(), cozinhaAtualizada.getNome());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinhaRepository.remover(cozinha);

    }

    
}