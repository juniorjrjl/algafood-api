package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

    private final RestauranteRepository restauranteRepository;

    private final CadastroFormaPagamentoService cadastroFormaPagamento;

    private final CadastroCozinhaService cadastroCozinha;

    private final CadastroCidadeService cadastroCidade;

    private final CadastroUsuarioService cadastroUsuario;

    private final PedidoRepository pedidoRepository;
    
    public boolean usuarioPodeGerenciarPedido(final Long idUsuario, final String codigoPedido) {
    	return pedidoRepository.usuarioPodeGerenciarPedido(idUsuario, codigoPedido);
    }
    
    public Restaurante buscar(final Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
    
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }
    
    public boolean existeResponsavel(final Long restauranteId, final Long usuarioId) {
    	return restauranteRepository.existeResponsavel(restauranteId, usuarioId);
    }

    @Transactional
    public Restaurante salvar(final Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
        Cidade cidade = cadastroCidade.buscar(cidadeId);
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }


    @Transactional
    public void ativar(final List<Long> restaurantesIds){
        restaurantesIds.forEach(this::ativar);
    }

    @Transactional
    public void ativar(final Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(final List<Long> restaurantesIds){
        restaurantesIds.forEach(this::inativar);
    }

    @Transactional
    public void inativar(final Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.inativar();
    }

    @Transactional
    public void abrir(final Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(final Long id){
        Restaurante restauranteAtual = buscar(id);
        restauranteAtual.fechar();
    }

    @Transactional
    public void associarFormaPagamento(final Long restauranteId, final Long formaPagamentoId){
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
        restaurante.getFormasPagamento().add(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(final Long restauranteId, final Long formaPagamentoId){
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
        restaurante.getFormasPagamento().remove(formaPagamento);
    }

    @Transactional
    public void associarUsuario(final Long restauranteId, final Long usuarioId){
        Restaurante restaurante = buscar(restauranteId);
        Usuario formaPagamento = cadastroUsuario.buscar(usuarioId);
        restaurante.getUsuarios().add(formaPagamento);
    }

    @Transactional
    public void desassociarUsuario(final Long restauranteId, final Long usuarioId){
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        restaurante.getUsuarios().remove(usuario);
    }

}