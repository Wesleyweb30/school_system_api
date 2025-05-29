package com.alpha.school_system_api.dtos;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EscolaDTO {
    private UUID id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String diretor;
        
}
