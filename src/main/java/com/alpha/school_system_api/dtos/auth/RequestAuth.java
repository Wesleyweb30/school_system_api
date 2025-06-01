package com.alpha.school_system_api.dtos.auth;

import lombok.Data;

@Data
public class RequestAuth {
    private String email;
    private String senha;
}