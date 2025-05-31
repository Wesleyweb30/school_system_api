package com.alpha.school_system_api.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alpha.school_system_api.dtos.AlunoRegisterRequest;
import com.alpha.school_system_api.dtos.AuthRequest;
import com.alpha.school_system_api.dtos.AuthResponse;
import com.alpha.school_system_api.dtos.RegisterEscolaRequest;
import com.alpha.school_system_api.dtos.UsuarioResponseDTO;
import com.alpha.school_system_api.infra.JwtUtil;
import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.Escola;
import com.alpha.school_system_api.model.user.Role;
import com.alpha.school_system_api.model.user.TipoUsuario;
import com.alpha.school_system_api.model.user.Usuario;
import com.alpha.school_system_api.repository.AlunoRepository;
import com.alpha.school_system_api.repository.EscolaRepository;
import com.alpha.school_system_api.repository.RoleRepository;
import com.alpha.school_system_api.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AlunoRepository alunoRepo;

    @Autowired
    private EscolaRepository escolaRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.gerarToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register/aluno")
    public ResponseEntity<?> registrarAluno(@RequestBody AlunoRegisterRequest request) {

        if (usuarioRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("E-mail já cadastrado.");
        }

        // 1. Busca a role padrão de usuário
        Role roleUsuario = roleRepo.findByNome("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("Papel ROLE_USUARIO não encontrado"));

        // 2. Cria o usuário base
        Usuario usuarioAluno = new Usuario();
        usuarioAluno.setNome(request.getNome());
        usuarioAluno.setEmail(request.getEmail());
        usuarioAluno.setSenha(passwordEncoder.encode(request.getSenha()));
        usuarioAluno.setTipo(TipoUsuario.ALUNO);
        usuarioAluno.setRoles(Set.of(roleUsuario));
        usuarioRepo.save(usuarioAluno);

        // 3. Cria o aluno vinculado ao usuário
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setDataNascimento(request.getDataNascimento());
        aluno.setUsuario(usuarioAluno);
        alunoRepo.save(aluno);

        return ResponseEntity.status(201).body("Aluno registrado com sucesso.");
    }

    @PostMapping("/register/escola")
    public ResponseEntity<?> registrarEscola(@RequestBody RegisterEscolaRequest request) {
        if (usuarioRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("E-mail já cadastrado.");
        }

        Role roleAdmin = roleRepo.findByNome("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Papel ROLE_ADMIN não encontrado"));

        // Criar e salvar o usuário da escola
        Usuario usuarioEscola = new Usuario();
        usuarioEscola.setNome(request.getNome());
        usuarioEscola.setEmail(request.getEmail());
        usuarioEscola.setSenha(passwordEncoder.encode(request.getSenha()));
        usuarioEscola.setTipo(TipoUsuario.ESCOLA);
        usuarioEscola.setRoles(Set.of(roleAdmin));

        usuarioRepo.save(usuarioEscola);

        // Criar e salvar a escola associada ao usuário
        Escola escola = new Escola();
        escola.setNome(request.getNome());
        escola.setCnpj(request.getCnpj());
        escola.setTelefone(request.getTelefone());
        escola.setDiretor(request.getDiretor());
        escola.setUsuario(usuarioEscola);

        escolaRepo.save(escola);

        return ResponseEntity.status(201).body("Escola registrada com sucesso.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String email = authentication.getName(); // pega o email do usuário logado (do token JWT)

        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Converte roles para string
        Set<String> roles = usuario.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet());

        UsuarioResponseDTO response = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo().name(), // ALUNO, PROFESSOR ou ESCOLA
                roles);

        return ResponseEntity.ok(response);
    }

}