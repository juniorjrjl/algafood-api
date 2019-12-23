package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Cidade> buscar(Long id){
        return cidadeRepository.findById(id);
    }

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }
    
    public Cidade salvar(Cidade cidade){
        Long cidadeId = cidade.getEstado().getId();
        Optional<Estado> estado = cadastroEstadoService.buscar(cidadeId);
        cidade.setEstado(estado
            .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com código %d", cidadeId))));
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id){
        try{
            cidadeRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com o código", id));
        }
    }

}