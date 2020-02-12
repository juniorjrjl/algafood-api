package com.algaworks.algafood.domain.service;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.freteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.nomeSemelhante;

import java.util.List;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    public Restaurante buscar(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }
    
    public List<Restaurante> listarFreteGratis(String nome){
        return restauranteRepository.findAll(freteGratis().and(nomeSemelhante(nome)));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
        Cidade cidade = cadastroCidade.buscar(cidadeId);
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }


    @Transactional
    public void ativar(List<Long> restaurantesIds){
        restaurantesIds.forEach(this::ativar);
    }

    @Transactional
    public void ativar(Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(List<Long> restaurantesIds){
        restaurantesIds.forEach(this::inativar);
    }

    @Transactional
    public void inativar(Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.inativar();
    }

    @Transactional
    public void abrir(Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.fechar();
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
        restaurante.getFormasPagamento().add(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
        restaurante.getFormasPagamento().remove(formaPagamento);
    }

    @Transactional
    public void associarUsuario(Long restauranteId, Long usuarioId){
        Restaurante restaurante = buscar(restauranteId);
        Usuario formaPagamento = cadastroUsuario.buscar(usuarioId);
        restaurante.getUsuarios().add(formaPagamento);
    }

    @Transactional
    public void desassociarUsuario(Long restauranteId, Long usuarioId){
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        restaurante.getUsuarios().remove(usuario);
    }

}