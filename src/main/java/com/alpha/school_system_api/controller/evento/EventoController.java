package com.alpha.school_system_api.controller.evento;

import com.alpha.school_system_api.dtos.evento.EventoDTO;
import com.alpha.school_system_api.dtos.evento.RequestRegisterEvento;
import com.alpha.school_system_api.service.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody RequestRegisterEvento req, Authentication authentication) {
        if (req.getNome() == null || req.getData() == null || req.getCep() == null) {
            return ResponseEntity.badRequest().body("Dados incompletos para criar o evento.");
        }
        try {
            eventoService.criarEvento(req, authentication.getName());
            return ResponseEntity.status(201).body("Evento criado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao criar evento." + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listar() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    @GetMapping("/por-escola")
    public ResponseEntity<?> listarEventosPorEscola(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String email) {
        try {
            List<EventoDTO> eventos = eventoService.listarEventosPorEscola(
                    Optional.ofNullable(id),
                    Optional.ofNullable(email));
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_USUARIO')")
    @PostMapping("/{eventoId}/inscrever")
    public ResponseEntity<?> inscrever(@PathVariable UUID eventoId, Authentication authentication) {
        try {
            eventoService.inscreverAluno(eventoId, authentication.getName());
            return ResponseEntity.ok("Aluno inscrito com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
