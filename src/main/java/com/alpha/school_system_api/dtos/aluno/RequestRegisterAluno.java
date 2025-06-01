package com.alpha.school_system_api.dtos.aluno;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RequestRegisterAluno {
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
}
