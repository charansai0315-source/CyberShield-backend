package com.cybershield.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Component
public class MachineLearningClient {
    private final RestClient client;

    public MachineLearningClient(RestClient mlRestClient) {
        this.client = mlRestClient;
    }

    public PredictionResult predict(TrafficRequest request) {
        try {
            PredictionResult result = client.post()
                    .uri("/predict")
                    .body(request)
                    .retrieve()
                    .body(PredictionResult.class);
            if (result == null) throw new IllegalStateException("The model returned an empty response.");
            return result;
        } catch (RestClientException ex) {
            throw new ModelUnavailableException("The traffic classification model is unavailable.", ex);
        }
    }

    public Map<String, Object> health() {
        try {
            return client.get()
                    .uri("/health")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() { });
        } catch (RestClientException ex) {
            return Map.of("status", "DOWN");
        }
    }
}
