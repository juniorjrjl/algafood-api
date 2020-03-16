package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.algaworks.algafood.domain.model.StatusPedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {

    @ApiModelProperty(value = "Código do pedido do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;
    
    @ApiModelProperty(value = "valor total dos itens do pedido sem o frete", example = "10.00")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "taxa de frete do pedido", example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(value = "soma dos itens e do frete do pedido", example = "10.00")
    private BigDecimal valorTotal;

    @ApiModelProperty(value = "situação atual do pedido", example = "CRIADO")
    private StatusPedido status;

    @ApiModelProperty(value = "data de criação do pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(value = "data de confirmação do pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(value = "data de cancelamento inicial do pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataCancelamento;

    @ApiModelProperty(value = "data de entrega inicial do pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataEntrega;
    
    private RestauranteResumoModel restaurante;

    private UsuarioModel cliente;

    private FormaPagamentoModel formaPagamento;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}