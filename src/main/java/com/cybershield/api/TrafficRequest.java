package com.cybershield.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Locale;

public record TrafficRequest(
        @NotBlank @Pattern(regexp = "(?i)^(TCP|UDP|ICMP)$") String protocol,
        @NotBlank String sourceIp,
        @NotBlank String destinationIp,
        @Min(0) @Max(65535) int sourcePort,
        @Min(0) @Max(65535) int destinationPort,
        @PositiveOrZero long flowDurationMs,
        @PositiveOrZero long packetsForward,
        @PositiveOrZero long packetsBackward,
        @PositiveOrZero long bytesForward,
        @PositiveOrZero long bytesBackward,
        @Min(0) @Max(10000) int failedLogins
) {
    public TrafficRequest {
        if (protocol != null) protocol = protocol.toUpperCase(Locale.ROOT);
    }
}
