package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;

    private final CadastroRestauranteService cadastroRestaurate;

    private final CadastroProdutoService cadastroProduto;

    private final CadastroUsuarioService cadastroUsuario;

    private final CadastroCidadeService cadastroCidade;

    private final CadastroFormaPagamentoService cadastroFormaPagamento;

    public Pedido buscar(final String codigo){
        return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
    }

    public Page<Pedido> listar(final PedidoFilter filtro, final Pageable pageable){
        return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
    }
    
    @Transactional
    public Pedido salvar(final Pedido pedido){
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

    private void carregarInformacoes(final Pedido pedido){
        Long clienteId = pedido.getCliente().getId();
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        pedido.setCliente(cadastroUsuario.buscar(clienteId));
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        Long restauranteId = pedido.getRestaurante().getId();
        pedido.getEnderecoEntrega().setCidade(cadastroCidade.buscar(cidadeId));
        pedido.setFormaPagamento(cadastroFormaPagamento.buscar(formaPagamentoId));
        pedido.setRestaurante(cadastroRestaurate.buscar(restauranteId));
    }

    private void gerarItensPedido(final Pedido pedido, final Long restauranteId, final BigDecimal taxaFrete){
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