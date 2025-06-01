package com.alpha.school_system_api.dtos.usuario;

import java.util.Set;
import java.util.UUID;

public record ResponseUsuarioDTO(
    UUID id,
    String nome,
    String email,
    String tipo,
    Set<String> roles
) {}
