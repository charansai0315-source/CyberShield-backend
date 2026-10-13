package com.cybershield.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TrafficController {
    private final TrafficAnalysisService service;
    private final MachineLearningClient modelClient;

    public TrafficController(TrafficAnalysisService service, MachineLearningClient modelClient) {
        this.service = service;
        this.modelClient = modelClient;
    }

    @PostMapping("/traffic/analyze")
    public TrafficAnalysis analyze(@Valid @RequestBody TrafficRequest request) {
        return service.analyze(request);
    }

    @PostMapping("/traffic/demo")
    public List<TrafficAnalysis> loadDemoTraffic() {
        return service.loadDemoTraffic();
    }

    @GetMapping("/traffic/recent")
    public List<TrafficAnalysis> recent(@RequestParam(defaultValue = "16") int limit) {
        return service.recent(limit);
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummary summary() {
        return service.summary();
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> model = modelClient.health();
        return Map.of("status", "UP", "service", "cybershield-api", "model", model);
    }
}
