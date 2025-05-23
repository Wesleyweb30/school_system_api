package com.alpha.school_system_api.dtos;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.Evento;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EventoDTO {

    private UUID id;
    private String nome;
    private LocalDate data;
    private List<AlunoDTO> alunos;

     public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.data = evento.getData();
        this.alunos = evento.getAlunos().stream()
                .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
                .collect(Collectors.toList());
    }

}
