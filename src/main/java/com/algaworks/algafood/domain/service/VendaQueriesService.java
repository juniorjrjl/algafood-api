package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueriesService {

    List<VendaDiaria> consultarVendasDiarias(final VendaDiariaFilter filtro, final String timeOffset);
    
}