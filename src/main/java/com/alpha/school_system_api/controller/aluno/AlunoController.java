package com.alpha.school_system_api.controller.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.school_system_api.dtos.aluno.AlunoDTO;
import com.alpha.school_system_api.repository.AlunoRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepo;

    // @PostMapping
    // public Aluno criar(@RequestBody Aluno aluno) {
    //     return alunoRepo.save(aluno);
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AlunoDTO> listar() {
        return alunoRepo.findAll().stream()
                .map(aluno -> new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail()))
                .toList();
    }
}
