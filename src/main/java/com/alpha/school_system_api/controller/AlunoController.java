package com.alpha.school_system_api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.service.AlunoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Aluno> buscarPorId(@PathVariable UUID id) {
        return alunoService.buscarPorId(id);
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable UUID id, @RequestBody Aluno alunoAtualizado) {
        return alunoService.atualizar(id, alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        alunoService.deletar(id);
    }
}