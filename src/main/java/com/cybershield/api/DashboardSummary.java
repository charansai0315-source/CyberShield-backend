package com.cybershield.api;

import java.util.Map;

public record DashboardSummary(
        long totalAnalyzed,
        long threatsDetected,
        long benignCount,
        double threatRate,
        double averageConfidence,
        Map<String, Long> categoryCounts,
        Map<String, Long> severityCounts
) { }
