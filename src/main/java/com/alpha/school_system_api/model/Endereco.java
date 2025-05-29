package com.alpha.school_system_api.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

    private String numero;
    private String referencia;
}
