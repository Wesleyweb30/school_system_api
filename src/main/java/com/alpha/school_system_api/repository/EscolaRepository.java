package com.alpha.school_system_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.school_system_api.model.Escola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, UUID> {}
