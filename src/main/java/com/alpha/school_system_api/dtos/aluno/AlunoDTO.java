package com.alpha.school_system_api.dtos.aluno;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlunoDTO {
    private UUID id;
    private String nome;
    private String email;
}
