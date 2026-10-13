package com.cybershield.api;

import java.time.Instant;

public record TrafficAnalysis(
        long id,
        Instant createdAt,
        String protocol,
        String sourceIp,
        String destinationIp,
        int sourcePort,
        int destinationPort,
        long flowDurationMs,
        long packetsForward,
        long packetsBackward,
        long bytesForward,
        long bytesBackward,
        int failedLogins,
        String code,
        String category,
        String severity,
        double confidence,
        String summary
) { }
