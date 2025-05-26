package com.alpha.school_system_api.controller;

import com.alpha.school_system_api.dtos.EventoDTO;
import com.alpha.school_system_api.dtos.EventoRequest;
import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.Endereco;
import com.alpha.school_system_api.model.Evento;
import com.alpha.school_system_api.repository.AlunoRepository;
import com.alpha.school_system_api.repository.EventoRepository;
import com.alpha.school_system_api.service.ViaCepService;

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

    @Autowired
    private ViaCepService viaCepService;

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarEvento(@RequestBody EventoRequest req) {
        try {
            Evento evento = new Evento();
            evento.setNome(req.getNome());
            evento.setData(req.getData());

            // Busca endereço no ViaCEP
            Map<String, String> dados = viaCepService.buscarEnderecoPorCep(req.getCep());

            Endereco endereco = new Endereco();
            endereco.setCep(req.getCep());
            endereco.setLogradouro(dados.get("logradouro"));
            endereco.setBairro(dados.get("bairro"));
            endereco.setCidade(dados.get("localidade"));
            endereco.setEstado(dados.get("uf"));
            endereco.setNumero(req.getNumero());
            endereco.setReferencia(req.getReferencia());

            evento.setEndereco(endereco);

            eventoRepository.save(evento);

            return ResponseEntity.status(201).body("Evento criado com sucesso.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao criar evento.");
        }
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
