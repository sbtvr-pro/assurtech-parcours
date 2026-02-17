package com.assurtech.quote_service.infrastructure.adapters.out.external;

import org.springframework.web.client.RestClient;

import com.assurtech.quote_service.domain.ports.out.PricingClientPort;
import com.assurtech.quote_service.domain.ports.out.PricingResponse;

public class RuleEngineClient implements PricingClientPort {
  private final RestClient restClient;

    public RuleEngineClient() {
        this.restClient = RestClient.builder().baseUrl("http://localhost:8081").build();
    }

    @Override
    public PricingResponse getPricing(int age, int claims, String carType) {
        return restClient.post()
            .uri("/api/rules/calculate")
            .body(new RuleRequest(age, 5, claims, carType)) // 5 ans permis par défaut
            .retrieve()
            .body(PricingResponse.class);
    }

    private record RuleRequest(int age, int licenseYears, int claimCount, String carType) {}
    
}
