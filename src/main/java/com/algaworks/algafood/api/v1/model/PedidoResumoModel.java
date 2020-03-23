package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoModel> {

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

    private RestauranteResumoModel restaurante;
    
    private UsuarioModel cliente;

}