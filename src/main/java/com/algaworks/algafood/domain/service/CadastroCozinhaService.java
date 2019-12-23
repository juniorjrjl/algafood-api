package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha buscar(Long id){
        return cozinhaRepository.buscar(id);
    }

    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }
    
    public void excluir(Long id){
        try{
            cozinhaRepository.remover(id);
        } catch(EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com o código", id));
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
        }
    }

}