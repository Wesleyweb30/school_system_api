package com.alpha.school_system_api.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AlunoRegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
}
