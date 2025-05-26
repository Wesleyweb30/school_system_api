package com.alpha.school_system_api.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ViaCepService {

    @SuppressWarnings("unchecked")
    public Map<String, String> buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> dados = mapper.readValue(response, Map.class);

            if (dados.containsKey("erro") && Boolean.parseBoolean(dados.get("erro"))) {
                throw new IllegalArgumentException("CEP inválido.");
            }

            return dados;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar CEP no ViaCEP: " + e.getMessage());
        }
    }

}
