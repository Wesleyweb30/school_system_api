package com.alpha.school_system_api.dtos.evento;

import com.alpha.school_system_api.dtos.aluno.AlunoDTO;
import com.alpha.school_system_api.model.Endereco;
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
    private Endereco endereco;
    private List<AlunoDTO> alunos;

     public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.data = evento.getData();
        this.endereco = evento.getEndereco();
        this.alunos = evento.getAlunos().stream()
        .filter(aluno -> aluno.getUsuario() != null) // Evita erro de fetch
        .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
        .collect(Collectors.toList());

    }

}
