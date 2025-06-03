package com.alpha.school_system_api.controller.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.school_system_api.dtos.aluno.AlunoDTO;
import com.alpha.school_system_api.dtos.aluno.RequestUpdateAlunoDTO;
import com.alpha.school_system_api.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listar() {
        try {
            List<AlunoDTO> alunos = alunoService.listarTodos();
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PreAuthorize("hasRole('ROLE_USUARIO')")
    @PutMapping("/me")
    public ResponseEntity<?> atualizarAluno(@RequestBody RequestUpdateAlunoDTO request, Authentication authentication) {
        String emailUsuario = authentication.getName(); // pega do token JWT
        try {
            AlunoDTO alunoAtualizado = alunoService.atualizarAluno(emailUsuario, request);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
