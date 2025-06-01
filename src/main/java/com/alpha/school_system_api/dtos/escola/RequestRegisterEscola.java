package com.alpha.school_system_api.dtos.escola;

import lombok.Data;

@Data
public class RequestRegisterEscola {
    private String nome;
    private String email;
    private String senha;
    private String cnpj;
    private String telefone;
    private String diretor;

}
