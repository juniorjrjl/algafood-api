package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = 
        "Forma de Pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscar(Long id){
        return formaPagamentoRepository.findById(id)
            .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }
    
    @Transactional
    public void excluir(Long id){
        try{
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch(EmptyResultDataAccessException ex){
            throw new FormaPagamentoNaoEncontradaException(id);
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }
    
}