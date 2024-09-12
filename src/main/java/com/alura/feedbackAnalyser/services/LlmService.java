package com.alura.feedbackAnalyser.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LlmService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    // Nova URL do LM Studio rodando na porta 1234
    private static final String LM_STUDIO_URL = "http://localhost:1234/v1/completions";

    // Construtor
    public LlmService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public LlmService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String analyzeSentiment(String feedbackText) {
        try {
            // Criar o corpo da requisição JSON para o modelo
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", String.format(
                    "Classifique o feedback a seguir como POSITIVO, NEGATIVO ou INCONCLUSIVO: %s", feedbackText));
            requestBody.put("max_tokens", 50); // Limitar tokens a 5 para forçar uma resposta curta
            requestBody.put("temperature", 0.0); // Ajustar temperatura para garantir respostas previsíveis
            requestBody.put("model", "phi-3.1");

            // Configurar os cabeçalhos
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Criar a entidade da requisição
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Fazer a requisição para o LM Studio e obter a resposta
            String response = restTemplate.postForObject(LM_STUDIO_URL, request, String.class);

            // Analisar a resposta JSON para extrair o sentimento classificado
            JsonNode root = objectMapper.readTree(response);
            String completion = root.path("choices").get(0).path("text").asText().trim();

            // Retornar o sentimento classificado
            return classifySentiment(completion);

        } catch (Exception e) {
            e.printStackTrace();
            return "INCONCLUSIVO"; // Retorno padrão em caso de erro
        }
    }

    private String classifySentiment(String completion) {
        completion = completion.toLowerCase();

        if (completion.contains("positivo")) {
            return "POSITIVO";
        } else if (completion.contains("negativo")) {
            return "NEGATIVO";
        } else if (completion.contains("inconclusivo")) {
            return "INCONCLUSIVO";
        } else {
            return "INCONCLUSIVO";
        }
    }
}
