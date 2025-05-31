package com.alpha.school_system_api.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventoRequest {
    private String nome;
    private LocalDate data;
    private String cep;
    private String numero;
    private String referencia;
}
