package com.alpha.school_system_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.school_system_api.model.Aluno;
import com.alpha.school_system_api.model.usuario.Usuario;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID>{
    Optional<Aluno> findByUsuario(Usuario usuario);
    Optional<Aluno> findByEmail(String email);
}
