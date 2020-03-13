package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

    @ApiModelProperty(value = "ID do cliente", example = "1")
    private Long clienteId;
    @ApiModelProperty(value = "ID do restaurante", example = "1")
    private Long restauranteId;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @ApiModelProperty(value = "data de criação inicial do pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataCriacaoInicio;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @ApiModelProperty(value = "data de criação finaldo pedido", example = "2019-12-01T18:09:02.70844Z")
    private OffsetDateTime dataCriacaoFim;
    
}