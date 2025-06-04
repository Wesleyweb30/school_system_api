package com.alpha.school_system_api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alpha.school_system_api.model.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 60, nullable = false)
    private String nome;

    @Column(length = 18, unique = true)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String diretor;

    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<Evento> eventos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
