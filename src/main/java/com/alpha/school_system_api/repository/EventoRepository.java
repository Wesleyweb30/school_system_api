package com.alpha.school_system_api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.school_system_api.model.Escola;
import com.alpha.school_system_api.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {
    Optional<List<Evento>> findByEscola(Escola escola);
}

