package com.assurtech.quote_service.infrastructure.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {

    private final String ruleEngineUrl;
    private final RestClient restClient;

    public ExternalServiceHealthIndicator(@Value("${rule.engine.url:http://localhost:8083}") String ruleEngineUrl) {
        this.ruleEngineUrl = ruleEngineUrl;
        this.restClient = RestClient.builder().baseUrl(ruleEngineUrl).build();
    }

    @Override
    public Health health() {
        try {
            // On tente un ping sur le health check du rule engine
            restClient.get()
                    .uri("/actuator/health")
                    .retrieve()
                    .toBodilessEntity();
            return Health.up().withDetail("rule-engine", "Available at " + ruleEngineUrl).build();
        } catch (Exception e) {
            return Health.down().withDetail("rule-engine", "Unavailable at " + ruleEngineUrl)
                    .withException(e).build();
        }
    }
}
