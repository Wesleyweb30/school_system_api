package com.alpha.school_system_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.repository.AlunoRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepo;

    @PostMapping
    public Aluno criar(@RequestBody Aluno aluno) {
        return alunoRepo.save(aluno);
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoRepo.findAll();
    }
}
