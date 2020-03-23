package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.v1.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FotoProduto;
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
        modelMapper.createTypeMap(FotoProdutoInput.class, FotoProduto.class)
            .<String>addMapping(src -> src.getArquivo().getContentType(), (dest, value) -> dest.setContentType(value))
            .<Long>addMapping(src -> src.getArquivo().getSize(), (dest, value) -> dest.setTamanho(value))
            .<String>addMapping(src -> src.getArquivo().getOriginalFilename(), (dest, value) -> dest.setNomeArquivo(value));

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
            .addMappings(mapper -> mapper.skip(Cidade::setId));
        return modelMapper;
    }
    
}