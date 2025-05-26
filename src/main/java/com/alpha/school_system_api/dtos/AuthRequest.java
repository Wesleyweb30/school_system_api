package com.alpha.school_system_api.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
}