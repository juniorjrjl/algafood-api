package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurate;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    public Pedido buscar(String codigo){
        return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
    }

    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }
    
    @Transactional
    public Pedido salvar(Pedido pedido){
        Restaurante restaurante = cadastroRestaurate.buscar(pedido.getRestaurante().getId());
        carregarInformacoes(pedido);                     
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        boolean formaPagamentoIndisponivel = restaurante.getFormasPagamento().stream()
            .filter(f -> f.getId() == formaPagamentoId).collect(Collectors.toSet()).isEmpty();
        if (formaPagamentoIndisponivel){
            throw new NegocioException(String
                                       .format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                                               pedido.getFormaPagamento().getDescricao()));
        }
        gerarItensPedido(pedido, restaurante.getId(), restaurante.getTaxaFrete());
        return pedidoRepository.save(pedido);
    }

    private void carregarInformacoes(Pedido pedido){
        Long clienteId = pedido.getCliente().getId();
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        pedido.setCliente(cadastroUsuario.buscar(clienteId));
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        Long restauranteId = pedido.getRestaurante().getId();
        pedido.getEnderecoEntrega().setCidade(cadastroCidade.buscar(cidadeId));
        pedido.setFormaPagamento(cadastroFormaPagamento.buscar(formaPagamentoId));
        pedido.setRestaurante(cadastroRestaurate.buscar(restauranteId));
    }

    private void gerarItensPedido(Pedido pedido, Long restauranteId, BigDecimal taxaFrete){
        pedido.setTaxaFrete(taxaFrete);
        for (int i = 0; i < pedido.getItens().size(); i++) {
            Long produtoId = pedido.getItens().get(i).getProduto().getId();
            Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
            Integer quantidade = pedido.getItens().get(i).getQuantidade();
            BigDecimal valoTotal = produto.getPreco().multiply(new BigDecimal(quantidade));
            pedido.getItens().get(i).setProduto(produto);
            pedido.getItens().get(i).setPrecoUnitario(produto.getPreco());
            pedido.getItens().get(i).setPrecoTotal(valoTotal);
        }
    }

}