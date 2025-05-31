package com.alpha.school_system_api.dtos;

import lombok.Data;

@Data
public class RegisterEscolaRequest {
    private String nome;
    private String email;
    private String senha;
    private String cnpj;
    private String telefone;
    private String diretor;

}
