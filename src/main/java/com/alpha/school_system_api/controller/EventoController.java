package com.alpha.school_system_api.controller;

import com.alpha.school_system_api.dtos.EventoDTO;
import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.Evento;
import com.alpha.school_system_api.repository.AlunoRepository;
import com.alpha.school_system_api.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AlunoRepository alunoRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<EventoDTO> listar() {
        return eventoRepository.findAll().stream()
                .map(evento -> new EventoDTO(evento))
                .collect(Collectors.toList());
    }

    // @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/{eventoId}/inscrever/{alunoId}")
    public ResponseEntity<?> inscrever(@PathVariable UUID eventoId, @PathVariable UUID alunoId) {
        Evento evento = eventoRepository.findById(eventoId).orElseThrow();
        Aluno aluno = alunoRepo.findById(alunoId).orElseThrow();

        if (!evento.getAlunos().contains(aluno)) {
            evento.getAlunos().add(aluno);
            eventoRepository.save(evento);
        }

        return ResponseEntity.ok("Aluno inscrito com sucesso.");
    }
}
