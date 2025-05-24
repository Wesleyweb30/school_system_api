package com.alpha.school_system_api.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 60, nullable = false)
    private String nome;
    private String cnpj;
    private String telefone;
    private String diretor;

     @ManyToMany(mappedBy = "escolas")
    private List<Evento> eventos = new ArrayList<>();

}
