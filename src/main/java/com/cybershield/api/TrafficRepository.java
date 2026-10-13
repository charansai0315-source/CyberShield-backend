package com.cybershield.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrafficRepository extends JpaRepository<TrafficEvent, Long> {
    List<TrafficEvent> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<TrafficEvent> findAllByOrderByCreatedAtDesc();
}
