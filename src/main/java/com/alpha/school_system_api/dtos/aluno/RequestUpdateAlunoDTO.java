package com.alpha.school_system_api.dtos.aluno;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateAlunoDTO {
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String senhaAtual;
    private String senhaNova;
}
