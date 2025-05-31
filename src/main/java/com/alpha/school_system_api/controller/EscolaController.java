package com.alpha.school_system_api.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.school_system_api.dtos.EscolaDTO;

import com.alpha.school_system_api.repository.EscolaRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping ("/escolas")
public class EscolaController {

    @Autowired
    private EscolaRepository escolaRepository;

    // @PostMapping    
    // public Escola criar(@RequestBody Escola escola){
    //     return escolaRepository.save(escola);
    // } 

    @GetMapping
    public List<EscolaDTO>listar(){
        return escolaRepository.findAll().stream()
            .map(escola -> new EscolaDTO(escola.getId(), escola.getNome(), escola.getCnpj(), escola.getTelefone(), escola.getDiretor()))
            .toList();
    }    


}
