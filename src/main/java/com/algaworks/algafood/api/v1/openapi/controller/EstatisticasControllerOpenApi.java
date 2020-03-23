package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> { }

    @ApiOperation(value = "Estatísticas", hidden = true)
	public EstatisticasModel estatisticas();

    @ApiOperation(value = "Busca dados de vendas", produces = "application/json, application/pdf")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "ID do Restaurante", name = "restauranteId", 
                          example = "1", dataType = "Long"),
        @ApiImplicitParam(value = "Data de criação inicial do pedido", name = "dataCriacaoInicio", 
                          example = "2019-12-01T18:09:02.70844Z", dataType = "date-time"),
        @ApiImplicitParam(value = "Data de criação final do pedido", name = "dataCriacaoFim", 
                          example = "2019-12-01T18:09:02.70844Z", dataType = "date-time"),
    })
    public List<VendaDiaria> consultar(VendaDiariaFilter filtro, String timeOffset);
 
    @ApiOperation(value = "Gera PDF das vendas", hidden = true)
    public ResponseEntity<byte[]> gerarRelatorio(VendaDiariaFilter filtro, String timeOffset);
    
}