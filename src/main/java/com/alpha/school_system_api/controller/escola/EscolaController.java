package com.alpha.school_system_api.controller.escola;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.school_system_api.dtos.escola.EscolaDTO;
import com.alpha.school_system_api.dtos.escola.RequestUpdateEscolaDTO;
import com.alpha.school_system_api.service.EscolaService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/escolas")
public class EscolaController {

    @Autowired
    private EscolaService escolaService;

    @GetMapping
    public ResponseEntity<List<EscolaDTO>> listar() {
        try {
            List<EscolaDTO> escolas = escolaService.listarTodas();
            return ResponseEntity.ok(escolas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/me")
    public ResponseEntity<?> atualizarEscola(Authentication authentication,
            @RequestBody RequestUpdateEscolaDTO request) {
        String emailUsuario = authentication.getName();
        EscolaDTO atualizada = escolaService.atualizar(emailUsuario, request);
        return ResponseEntity.ok(atualizada);
    }

}
