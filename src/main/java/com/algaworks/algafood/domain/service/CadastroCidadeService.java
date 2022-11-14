package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroCidadeService {

    private final CidadeRepository cidadeRepository;

    private final CadastroEstadoService cadastroEstado;

    public Cidade buscar(final Long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }
    
    @Transactional
    public Cidade salvar(final Cidade cidade){
        Long cidadeId = cidade.getEstado().getId();
        Estado estado = cadastroEstado.buscar(cidadeId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
    
    @Transactional
    public void excluir(final Long id){
        try{
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        } catch(EmptyResultDataAccessException ex){
            throw new CidadeNaoEncontradaException(id);
        }
    }

}