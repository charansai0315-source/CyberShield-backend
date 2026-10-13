package com.cybershield.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrafficAnalysisService {
    private final TrafficRepository repository;
    private final MachineLearningClient modelClient;

    public TrafficAnalysisService(TrafficRepository repository, MachineLearningClient modelClient) {
        this.repository = repository;
        this.modelClient = modelClient;
    }

    @Transactional
    public TrafficAnalysis analyze(TrafficRequest request) {
        PredictionResult prediction = modelClient.predict(request);
        return repository.save(new TrafficEvent(request, prediction)).toAnalysis();
    }

    @Transactional(readOnly = true)
    public List<TrafficAnalysis> recent(int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 100));
        return repository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, safeLimit))
                .stream().map(TrafficEvent::toAnalysis).toList();
    }

    @Transactional
    public List<TrafficAnalysis> loadDemoTraffic() {
        List<TrafficRequest> examples = List.of(
                new TrafficRequest("TCP", "10.0.0.24", "10.0.0.8", 52344, 443,
                        9200, 34, 29, 15600, 22000, 0),
                new TrafficRequest("UDP", "198.51.100.27", "10.0.0.8", 43120, 443,
                        120, 18000, 2, 8500000, 32, 0),
                new TrafficRequest("TCP", "203.0.113.77", "10.0.0.44", 58430, 22,
                        1500, 42, 1, 2260, 60, 0),
                new TrafficRequest("TCP", "198.51.100.92", "10.0.0.18", 50124, 22,
                        64000, 38, 14, 9200, 5800, 18)
        );
        return examples.stream().map(this::analyze).toList();
    }

    @Transactional(readOnly = true)
    public DashboardSummary summary() {
        List<TrafficEvent> events = repository.findAllByOrderByCreatedAtDesc();
        Map<String, Long> categories = new LinkedHashMap<>();
        Map<String, Long> severities = new LinkedHashMap<>();
        long threatCount = 0;
        double confidenceTotal = 0;

        for (TrafficEvent event : events) {
            categories.merge(event.getCategory(), 1L, Long::sum);
            severities.merge(event.getSeverity(), 1L, Long::sum);
            confidenceTotal += event.getConfidence();
            if (!"BENIGN".equals(event.getCode())) threatCount++;
        }

        long total = events.size();
        long benignCount = total - threatCount;
        double threatRate = total == 0 ? 0 : Math.round(threatCount * 1000.0 / total) / 10.0;
        double averageConfidence = total == 0 ? 0 : Math.round(confidenceTotal * 100.0 / total) / 100.0;
        return new DashboardSummary(total, threatCount, benignCount, threatRate,
                averageConfidence, categories, severities);
    }
}
