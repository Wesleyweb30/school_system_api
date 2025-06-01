package com.alpha.school_system_api.controller.evento;

import com.alpha.school_system_api.dtos.evento.EventoDTO;
import com.alpha.school_system_api.dtos.evento.RequestRegisterEvento;
import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.Endereco;
import com.alpha.school_system_api.model.Evento;
import com.alpha.school_system_api.model.usuario.TipoUsuario;
import com.alpha.school_system_api.model.usuario.Usuario;
import com.alpha.school_system_api.repository.AlunoRepository;
import com.alpha.school_system_api.repository.EventoRepository;
import com.alpha.school_system_api.repository.UsuarioRepository;
import com.alpha.school_system_api.service.ViaCepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private UsuarioRepository usuarioRepo;

    // ✅ Apenas ADMIN pode criar evento
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody RequestRegisterEvento req) {
        try {
            Evento evento = new Evento();
            evento.setNome(req.getNome());
            evento.setData(req.getData());

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

    // ✅ Usuários autenticados podem listar eventos
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping
    public List<EventoDTO> listar() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    // ✅ Apenas USUARIO pode se inscrever (aluno logado)
    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/{eventoId}/inscrever")
    public ResponseEntity<?> inscrever(@PathVariable UUID eventoId, Authentication authentication) {
        String email = authentication.getName();

        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getTipo().equals(TipoUsuario.ALUNO)) {
            return ResponseEntity.status(403).body("Apenas alunos podem se inscrever.");
        }

        Aluno aluno = alunoRepo.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (!evento.getAlunos().contains(aluno)) {
            evento.getAlunos().add(aluno);
            eventoRepository.save(evento);
        }

        return ResponseEntity.ok("Aluno inscrito com sucesso.");
    }
}
