package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueriesService;
import com.algaworks.algafood.domain.service.VendaReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EstatisticasController implements EstatisticasControllerOpenApi {

    private final VendaQueriesService vendaQueryService;

    private final VendaReportService vendaReportService;

    private final AlgaLinks algaLinks;

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultar(final VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00")final String timeOffset){
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }
 
    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorio(final VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") final String timeOffset){
        byte[] bytesPDF = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body(bytesPDF);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();
        estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
        return estatisticasModel;
}
    
}