package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha buscar(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }
    
    @Transactional
    public void excluir(Long id){
        try{
            cozinhaRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ex){
            throw new CozinhaNaoEncontradaException(id);
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }
    }

}