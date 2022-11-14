package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @Schema(example = "1")
    private String codigo;

    @Schema(example = "100.00")
    private BigDecimal subtotal;

    @Schema(example = "20.00")
    private BigDecimal taxaFrete;

    @Schema(example = "120.00")
    private BigDecimal valorTotal;

    @Schema(example = "CRIADO", enumAsRef = true)
    private StatusPedido status;

    @Schema(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataCriacao;

    @Schema(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataConfirmacao;

    @Schema(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataCancelamento;

    @Schema(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataEntrega;
    
    private RestauranteResumoModel restaurante;

    private UsuarioModel cliente;

    private FormaPagamentoModel formaPagamento;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}