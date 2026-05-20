package com.elainehello.imageintelligenceapi.client;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class HuggingFaceClient {

    private final RestTemplate restTemplate;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.token}")
    private String apiToken;

    public String analyzeImage(String imageUrl) {
        log.info("Calling Hugging Face API for image: {}", imageUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"inputs\": \"%s\"}", imageUrl);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            log.info("Hugging Face API responded with status: {}", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("Hugging Face API call failed: {}", e.getMessage());
            throw new ExternalApiException("Failed to reach Hugging Face API: " + e.getMessage());
        }
    }
}
