package com.elainehello.imageintelligenceapi.client;

import com.elainehello.imageintelligenceapi.client.dto.HuggingFaceResponse;
import com.elainehello.imageintelligenceapi.exception.ExternalApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HuggingFaceClient {

    private static final Logger log = LoggerFactory.getLogger(HuggingFaceClient.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.token}")
    private String apiToken;

    public HuggingFaceClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String analyzeImage(String imageUrl) {
        log.info("Calling Hugging Face API for: {}", imageUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"inputs\": \"%s\"}", imageUrl);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getBody() != null && response.getBody().contains("currently loading")) {
                log.warn("Model is loading, retrying in 25 seconds...");
                Thread.sleep(25000);
                return analyzeImage(imageUrl);
            }

            HuggingFaceResponse[] results = objectMapper.readValue(
                    response.getBody(),
                    HuggingFaceResponse[].class
            );

            if (results != null && results.length > 0) {
                String prediction = results[0].getGeneratedText();
                log.info("Prediction received: {}", prediction);
                return prediction;
            }

            throw new ExternalApiException("Empty response from Hugging Face API");

        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Hugging Face API call failed: {}", e.getMessage());
            throw new ExternalApiException("Failed to call Hugging Face API: " + e.getMessage());
        }
    }
}
