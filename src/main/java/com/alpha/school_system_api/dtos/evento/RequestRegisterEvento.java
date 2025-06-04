package com.alpha.school_system_api.dtos.evento;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RequestRegisterEvento {
    private String nome;
    private LocalDate data;
    private String cep;
    private String numero;
    private String referencia;
}
