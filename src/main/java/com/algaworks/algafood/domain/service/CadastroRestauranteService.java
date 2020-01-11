package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.freteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.nomeSemelhante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    public Optional<Restaurante> buscar(Long id){
        return restauranteRepository.findById(id);
    }

    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }
    
    public List<Restaurante> listarFreteGratis(String nome){
        return restauranteRepository.findAll(freteGratis().and(nomeSemelhante(nome)));
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscar(cozinhaId)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

}