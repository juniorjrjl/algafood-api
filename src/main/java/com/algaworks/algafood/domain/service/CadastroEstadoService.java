package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";

    private final EstadoRepository estadoRepository;

    public Estado buscar(final Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }


    @Transactional
    public Estado salvar(final Estado cozinha){
        return estadoRepository.save(cozinha);
    }
    

    @Transactional
    public void excluir(final Long id){
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