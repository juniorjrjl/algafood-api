package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassemblerV2 {

    @Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputV2 CozinhaInputV2) {
		return modelMapper.map(CozinhaInputV2, Cozinha.class);
	}
	
	public void copyToDomainInObject(CozinhaInputV2 CozinhaInputV2, Cozinha cozinha){
		modelMapper.map(CozinhaInputV2, cozinha);
	}

}