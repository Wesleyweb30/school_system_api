package com.alpha.school_system_api.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseAuth {
    private String token;
}
