package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageableTranslator {

    public static Pageable translate(final Pageable pageable, final Map<String, String> fieldsMappging){
        var orders = pageable.getSort().stream()
            .filter(order -> fieldsMappging.containsKey(order.getProperty()))
            .map(order -> new Sort.Order(order.getDirection(), fieldsMappging.get(order.getProperty())))
            .collect(Collectors.toList());
        
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}