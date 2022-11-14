package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Estatísticas", description = "Busca dados estatísticos")
public interface EstatisticasControllerOpenApi {

    class EstatisticasModel extends RepresentationModel<EstatisticasModel> { }

	EstatisticasModel estatisticas();

    @Operation(summary = "Busca dados de vendas diárias")
    List<VendaDiaria> consultar(final VendaDiariaFilter filtro,
                                @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UT", example = "+00:00") final String timeOffset);

    @Operation(summary = "Gera um relatório em pdf de vendas diárias")
    ResponseEntity<byte[]> gerarRelatorio(final VendaDiariaFilter filtro,
                                          @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UT", example = "+00:00")final String timeOffset);
    
}