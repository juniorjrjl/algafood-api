package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    public Cidade buscar(Long id){
        return cidadeRepository.buscar(id);
    }

    public List<Cidade> listar(){
        return cidadeRepository.listar();
    }
    
    public Cidade salvar(Cidade cidade){
        Long cidadeId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscar(cidadeId);
        if (estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com código %d", cidadeId));
        }
        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public void excluir(Long id){
        try{
            cidadeRepository.remover(id);
        } catch(EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com o código", id));
        }
    }

}