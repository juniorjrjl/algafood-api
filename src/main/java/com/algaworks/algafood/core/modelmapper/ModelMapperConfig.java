package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {

    @Bean
    public  ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
            .<String>addMapping(src -> src.getCidade().getEstado().getNome(), 
            (dest, value) -> dest.getCidade().setEstado(value));
        modelMapper.createTypeMap(SenhaUsuarioInput.class, Usuario.class)
            .addMapping(SenhaUsuarioInput::getNovaSenha, Usuario::setSenha);
        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
            .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }
    
}