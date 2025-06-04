package com.alpha.school_system_api.dtos.escola;


import java.util.List;
import java.util.UUID;

import com.alpha.school_system_api.dtos.evento.EventoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EscolaDTO {
    private UUID id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String diretor;
    private List<EventoDTO> eventos;

        
}
