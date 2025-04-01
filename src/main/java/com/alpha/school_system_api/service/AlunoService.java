package com.alpha.school_system_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(UUID id) {
        return alunoRepository.findById(id);
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(UUID id, Aluno alunoAtualizado) {
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

    public void deletar(UUID id) {
        alunoRepository.deleteById(id);
    }
}