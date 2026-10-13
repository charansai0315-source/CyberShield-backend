package com.cybershield.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Locale;

@Entity
@Table(name = "traffic_events", indexes = {
        @Index(name = "idx_traffic_created_at", columnList = "created_at"),
        @Index(name = "idx_traffic_code", columnList = "code")
})
public class TrafficEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, length = 8)
    private String protocol;

    @Column(name = "source_ip", nullable = false, length = 64)
    private String sourceIp;

    @Column(name = "destination_ip", nullable = false, length = 64)
    private String destinationIp;

    @Column(name = "source_port", nullable = false)
    private int sourcePort;

    @Column(name = "destination_port", nullable = false)
    private int destinationPort;

    @Column(name = "flow_duration_ms", nullable = false)
    private long flowDurationMs;

    @Column(name = "packets_forward", nullable = false)
    private long packetsForward;

    @Column(name = "packets_backward", nullable = false)
    private long packetsBackward;

    @Column(name = "bytes_forward", nullable = false)
    private long bytesForward;

    @Column(name = "bytes_backward", nullable = false)
    private long bytesBackward;

    @Column(name = "failed_logins", nullable = false)
    private int failedLogins;

    @Column(nullable = false, length = 32)
    private String code;

    @Column(nullable = false, length = 64)
    private String category;

    @Column(nullable = false, length = 16)
    private String severity;

    @Column(nullable = false)
    private double confidence;

    @Column(nullable = false, length = 500)
    private String summary;

    protected TrafficEvent() { }

    public TrafficEvent(TrafficRequest request, PredictionResult prediction) {
        this.protocol = request.protocol().toUpperCase(Locale.ROOT);
        this.sourceIp = request.sourceIp();
        this.destinationIp = request.destinationIp();
        this.sourcePort = request.sourcePort();
        this.destinationPort = request.destinationPort();
        this.flowDurationMs = request.flowDurationMs();
        this.packetsForward = request.packetsForward();
        this.packetsBackward = request.packetsBackward();
        this.bytesForward = request.bytesForward();
        this.bytesBackward = request.bytesBackward();
        this.failedLogins = request.failedLogins();
        this.code = prediction.code();
        this.category = prediction.category();
        this.severity = prediction.severity();
        this.confidence = prediction.confidence();
        this.summary = prediction.summary();
    }

    @PrePersist
    void setCreatedAt() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public TrafficAnalysis toAnalysis() {
        return new TrafficAnalysis(id, createdAt, protocol, sourceIp, destinationIp,
                sourcePort, destinationPort, flowDurationMs, packetsForward,
                packetsBackward, bytesForward, bytesBackward, failedLogins,
                code, category, severity, confidence, summary);
    }

    public String getCode() { return code; }
    public String getCategory() { return category; }
    public String getSeverity() { return severity; }
    public double getConfidence() { return confidence; }
}
