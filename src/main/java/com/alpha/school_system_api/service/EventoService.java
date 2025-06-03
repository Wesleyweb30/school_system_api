package com.alpha.school_system_api.service;

import com.alpha.school_system_api.dtos.evento.EventoDTO;
import com.alpha.school_system_api.dtos.evento.RequestRegisterEvento;
import com.alpha.school_system_api.model.*;
import com.alpha.school_system_api.model.usuario.TipoUsuario;
import com.alpha.school_system_api.model.usuario.Usuario;
import com.alpha.school_system_api.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AlunoRepository alunoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ViaCepService viaCepService;

    public void criarEvento(RequestRegisterEvento req) {
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
    }

    public List<EventoDTO> listarEventos() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    public void inscreverAluno(UUID eventoId, String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getTipo().equals(TipoUsuario.ALUNO)) {
            throw new RuntimeException("Apenas alunos podem se inscrever.");
        }

        Aluno aluno = alunoRepo.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (!evento.getAlunos().contains(aluno)) {
            evento.getAlunos().add(aluno);
            eventoRepository.save(evento);
        }
    }
}
