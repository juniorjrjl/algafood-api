package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NÃO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    PROPRIEDADE_INVALIDA("/propriedade-invalida", "Propriedade inválida"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem incompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido");
    
    private String title;
    private String uri;
    
    ProblemType(String path, String title){
        this.uri = String.format("https://algafood.com.br%s", path);
        this.title = title;
    }

}