package com.alpha.school_system_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Aluno> buscarPorId(@PathVariable UUID id) {
        return alunoRepository.findById(id);
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable UUID id, @RequestBody Aluno alunoAtualizado) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoAtualizado.getNome());
                    aluno.setDataNascimento(alunoAtualizado.getDataNascimento());
                    aluno.setGenero(alunoAtualizado.getGenero());
                    aluno.setTelefone(alunoAtualizado.getTelefone());
                    aluno.setEmail(alunoAtualizado.getEmail());
                    aluno.setCpf(alunoAtualizado.getCpf());
                    aluno.setNomeResponsavel(alunoAtualizado.getNomeResponsavel());
                    return alunoRepository.save(aluno);
                }).orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        alunoRepository.deleteById(id);
    }
}