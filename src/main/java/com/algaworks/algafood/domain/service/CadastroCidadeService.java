package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    public Cidade buscar(Long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }
    
    @Transactional
    public Cidade salvar(Cidade cidade){
        Long cidadeId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscar(cidadeId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
    
    @Transactional
    public void excluir(Long id){
        try{
            cidadeRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ex){
            throw new CidadeNaoEncontradaException(id);
        }
    }

}