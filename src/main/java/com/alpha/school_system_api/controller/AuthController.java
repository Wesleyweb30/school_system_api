package com.alpha.school_system_api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alpha.school_system_api.dtos.AuthRequest;
import com.alpha.school_system_api.dtos.AuthResponse;
import com.alpha.school_system_api.dtos.RegisterRequest;
import com.alpha.school_system_api.infra.JwtUtil;
import com.alpha.school_system_api.model.user.Role;
import com.alpha.school_system_api.model.user.Usuario;
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
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usuarioRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já está em uso.");
        }

        // Sempre atribui o papel de USUARIO
        Role roleUsuario = roleRepo.findByNome("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("ROLE_USUARIO não existe"));

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(request.getNome());
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(request.getSenha()));
        novoUsuario.setRoles(Set.of(roleUsuario));

        usuarioRepo.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
    }

}