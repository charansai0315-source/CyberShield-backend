package com.cybershield.api;

public record PredictionResult(
        String code,
        String category,
        String severity,
        double confidence,
        String summary
) { }
