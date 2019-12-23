package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Optional<Estado> buscar(Long id){
        return estadoRepository.findById(id);
    }

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado cozinha){
        return estadoRepository.save(cozinha);
    }
    
    public void excluir(Long id){
        try{
            estadoRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com o código", id));
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida, pois está em uso", id));
        }
    }
    
}