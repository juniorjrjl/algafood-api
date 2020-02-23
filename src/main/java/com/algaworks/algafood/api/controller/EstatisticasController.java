package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @RequestMapping("vendas-diarias")
    public List<VendaDiaria> consultar(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00")String timeOffset){
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }
    
}