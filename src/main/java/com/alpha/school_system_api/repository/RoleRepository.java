package com.alpha.school_system_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.school_system_api.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}
