package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueriesService;
import com.algaworks.algafood.domain.service.VendaReportService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;*/

@Service
@AllArgsConstructor
public class PDFVendaReportService implements VendaReportService {

    private final VendaQueriesService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(final VendaDiariaFilter filtro, final String timeOffset) {
        /*try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
            var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception ex) {
            throw new ReportException("Não foi possível emitir o relatório de vendas diarias", ex);
        }*/
        return null;
    }

    
}