package com.alpha.school_system_api.dtos.escola;

import lombok.Data;

@Data
public class RequestUpdateEscolaDTO {
    private String nome;
    private String cnpj;
    private String telefone;
    private String diretor;
    private String email;
    private String senhaAtual;
    private String senhaNova;
}
