package com.algaworks.algafood.api.assembler;

import java.util.Map;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    @Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainInObject(RestauranteInput restauranteInput, Restaurante restaurante){
		restaurante.setCozinha(new Cozinha());
		modelMapper.map(restauranteInput, restaurante);
	}
	
	public void copyToDomainInObject(Map<String, Object> properties, Restaurante restaurante){
		if (properties.containsKey("cozinha")){
			restaurante.setCozinha(new Cozinha());
		}
		modelMapper.map(properties, restaurante);
	}

}