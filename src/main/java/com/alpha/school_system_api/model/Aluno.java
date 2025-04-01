package com.alpha.school_system_api.model;


import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(length = 60, nullable = false)
    private String nome;

    private LocalDate dataNascimento;
    
    private String genero;
    
    private String telefone;
    
    private String email;
    
    private String cpf;
    
    private String nomeResponsavel;

}
