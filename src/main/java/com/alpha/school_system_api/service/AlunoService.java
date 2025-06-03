package com.alpha.school_system_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alpha.school_system_api.dtos.aluno.AlunoDTO;
import com.alpha.school_system_api.dtos.aluno.RequestUpdateAlunoDTO;
import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.usuario.Usuario;
import com.alpha.school_system_api.repository.AlunoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepo;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AlunoDTO> listarTodos() {
        return alunoRepo.findAll().stream()
                .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
                .collect(Collectors.toList());
    }

    public AlunoDTO atualizarAluno(String emailUsuario, RequestUpdateAlunoDTO request) {
        Aluno aluno = alunoRepo.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Usuario usuario = aluno.getUsuario();

        
        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        // Atualiza dados do aluno
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setDataNascimento(request.getDataNascimento());

        // Atualiza dados do usuário
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenhaNova()));

        // Salva no banco
        usuarioService.atualizarUsuario(usuario);
        alunoRepo.save(aluno);

        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }

}
