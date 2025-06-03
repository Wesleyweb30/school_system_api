package com.alpha.school_system_api.service;

import com.alpha.school_system_api.dtos.escola.RequestUpdateEscolaDTO;
import com.alpha.school_system_api.dtos.escola.EscolaDTO;
import com.alpha.school_system_api.model.Escola;
import com.alpha.school_system_api.model.usuario.Usuario;
import com.alpha.school_system_api.repository.EscolaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class EscolaService {

    @Autowired
    private EscolaRepository escolaRepo;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<EscolaDTO> listarTodas() {
        return escolaRepo.findAll().stream()
                .map(escola -> new EscolaDTO(escola.getId(), escola.getNome(), escola.getCnpj(), escola.getTelefone(), escola.getDiretor()))
                .toList();
    }

    public EscolaDTO atualizar(String emailUsuario, RequestUpdateEscolaDTO request) {
        Escola escola = escolaRepo.findByUsuarioEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada"));

        Usuario usuario = escola.getUsuario();

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha Atual incorreta");
        }

        escola.setNome(request.getNome());
        escola.setCnpj(request.getCnpj());
        escola.setTelefone(request.getTelefone());
        escola.setDiretor(request.getDiretor());

        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenhaNova()));

        escolaRepo.save(escola);
        usuarioService.atualizarUsuario(usuario);

        return new EscolaDTO(escola.getId(), escola.getNome(), escola.getCnpj(), escola.getTelefone(), escola.getDiretor());
    }
}
