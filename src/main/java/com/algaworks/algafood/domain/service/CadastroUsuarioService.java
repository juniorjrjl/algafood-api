package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Usuario buscar(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public Usuario buscar(Long id, String senha){
        return usuarioRepository.findByIdAndSenha(id, senha)
        		.orElseThrow(() -> new UsuarioNaoEncontradoException("A senha informada não conhecide com a senha do usuário"));
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioUsandoEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioUsandoEmail.isPresent()) {
            if (!usuarioUsandoEmail.get().equals(usuario)){
                throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail '%s'", usuario.getEmail()));
            }
            if (passwordEncoder.matches(usuarioUsandoEmail.get().getSenha(), usuario.getSenha())) {
            	throw new NegocioException("A senha informada não conhecide com a senha do usuário");
            }
        }
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void excluir(Long id){
        try{
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch(EmptyResultDataAccessException ex){
            throw new UsuarioNaoEncontradoException(id);
        }catch(DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
        }
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        usuario.getGrupos().add(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        usuario.getGrupos().remove(grupo);
    }

}