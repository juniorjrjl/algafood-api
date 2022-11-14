package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";
    private final GrupoRepository grupoRepository;

    public Grupo buscar(final Long id){
        return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    public List<Grupo> listar(){
        return grupoRepository.findAll();
    }

    @Transactional
    public Grupo salvar(final Grupo grupo){
        return grupoRepository.save(grupo);
    }
    
    @Transactional
    public void excluir(final Long id){
        try{
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch(EmptyResultDataAccessException ex){
            throw new GrupoNaoEncontradoException(id);
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

}