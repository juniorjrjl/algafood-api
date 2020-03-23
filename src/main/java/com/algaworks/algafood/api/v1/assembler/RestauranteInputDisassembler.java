package com.algaworks.algafood.api.v1.assembler;

import java.util.Map;

import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
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
		if (restaurante.getEndereco() != null){
			restaurante.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(restauranteInput, restaurante);
	}
	
	public void copyToDomainInObject(Map<String, Object> properties, Restaurante restaurante){
		if (properties.containsKey("cozinha")){
			restaurante.setCozinha(new Cozinha());
		}
		if (properties.containsKey("cidade")){
			restaurante.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(properties, restaurante);
	}

}