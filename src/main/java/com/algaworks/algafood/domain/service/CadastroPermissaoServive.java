package com.algaworks.algafood.domain.service;


import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPermissaoServive {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId){
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        Permissao permissao = permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
        grupo.getPermissoes().add(permissao);
    }

    @Transactional
    public void dessassociarPermissao(Long grupoId, Long permissaoId){
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        Permissao permissao = permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
        grupo.getPermissoes().remove(permissao);
    }
    
}