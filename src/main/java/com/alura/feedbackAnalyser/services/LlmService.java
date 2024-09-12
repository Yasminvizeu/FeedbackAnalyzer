package com.alura.feedbackAnalyser.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Map<String, Object> processFeedback(String feedbackText) {
        try {
            // Analisar o sentimento
            String sentiment = analyzeSentiment(feedbackText);

            // Identificar o código da funcionalidade
            String featureCode = identifyFeatureCode(feedbackText);

            // Identificar a razão para a implementação
            String reason = identifyReason(feedbackText);

            // Construir a lista de funcionalidades solicitadas
            Map<String, String> featureMap = new HashMap<>();
            featureMap.put("code", featureCode);
            featureMap.put("reason", reason);

            List<Map<String, String>> requestedFeatures = new ArrayList<>();
            requestedFeatures.add(featureMap);

            // Construir o JSON de resposta
            Map<String, Object> response = new HashMap<>();
            response.put("id", 1); // Pode substituir pelo ID real, se aplicável
            response.put("sentiment", sentiment);
            response.put("requested_features", requestedFeatures);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método genérico para realizar a requisição ao LM Studio
    private String makeLlmRequest(String prompt, int maxTokens, double temperature) {
        try {
            // Criar o corpo da requisição JSON para o modelo
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", maxTokens);
            requestBody.put("temperature", temperature);
            requestBody.put("model", "phi-3.1");

            // Configurar os cabeçalhos
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Criar a entidade da requisição
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Fazer a requisição para o LM Studio e obter a resposta
            String response = restTemplate.postForObject(LM_STUDIO_URL, request, String.class);

            // Analisar a resposta JSON
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").get(0).path("text").asText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "INCONCLUSIVO"; // Retorno padrão em caso de erro
        }
    }

    public String analyzeSentiment(String feedbackText) {
        String prompt = String.format("Classifique o feedback a seguir como POSITIVO, NEGATIVO ou INCONCLUSIVO: %s", feedbackText);
        String completion = makeLlmRequest(prompt, 50, 0.0);
        return classifySentiment(completion);
    }

    public String identifyFeatureCode(String feedbackText) {
        String prompt = String.format("Crie um código único e curto (exatamente 1 ou 2 palavra) que identifique a funcionalidade sugerida no feedback: %s", feedbackText);
        String completion = makeLlmRequest(prompt, 50, 0.2);
        return extractSolutionName(completion);
    }

    public String identifyReason(String feedbackText) {
        String prompt = String.format("Com base no feedback fornecido a seguir, explique em terceira pessoa a razão pela qual a funcionalidade sugerida deve ser implementada. O feedback é: %s", feedbackText);
        String completion = makeLlmRequest(prompt, 50, 0.8);
        return extractTextAfterNewLine(completion);
    }

    // Métodos auxiliares para classificar a resposta e extrair informaçõe

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

    private String extractSolutionName(String response) {
        String[] words = response.split("\\s+");
        // Verificar se há palavras na array
        if (words.length > 0) {
            // Retornar a última palavra
            return words[words.length - 1];
        } else {
            // Retornar mensagem se não houver palavras
            return "Nenhuma palavra encontrada";
        }
    }

    public static String extractTextAfterNewLine(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // Encontrar o índice do primeiro '\n'
        int index = text.indexOf('\n');

        if (index != -1 && index + 1 < text.length()) {
            // Retornar o texto após o primeiro '\n'
            return text.substring(index + 1).trim();
        } else {
            // Retornar uma string vazia se não houver '\n'
            return "";
        }
    }
}
