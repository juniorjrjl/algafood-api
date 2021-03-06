package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado buscar(Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }


    @Transactional
    public Estado salvar(Estado cozinha){
        return estadoRepository.save(cozinha);
    }
    

    @Transactional
    public void excluir(Long id){
        try{
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        } catch(EmptyResultDataAccessException ex){
            throw new EstadoNaoEncontradoException(id);
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }
    
}