package com.alpha.school_system_api.dtos;

import java.util.Set;
import java.util.UUID;

public record UsuarioResponseDTO(
    UUID id,
    String nome,
    String email,
    String tipo,
    Set<String> roles
) {}
