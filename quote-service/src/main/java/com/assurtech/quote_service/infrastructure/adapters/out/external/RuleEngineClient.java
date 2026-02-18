package com.assurtech.quote_service.infrastructure.adapters.out.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.assurtech.quote_service.domain.exception.PricingException;
import com.assurtech.quote_service.domain.model.RuleRequest;
import com.assurtech.quote_service.domain.ports.out.PricingClientPort;
import com.assurtech.quote_service.domain.ports.out.PricingResponse;

@Component
public class RuleEngineClient implements PricingClientPort {
  private final RestClient restClient;

    public RuleEngineClient() {
        this.restClient = RestClient.builder().baseUrl("http://localhost:8081").build();
    }

    @Override
    public PricingResponse getPricing(int age, int claims, String carType) {
        try {
              return restClient.post()
            .uri("/api/rules/calculate")
            .body(new RuleRequest(age, 5, claims, carType)) // 5 ans permis par défaut
            .retrieve()
            .body(PricingResponse.class);}
        catch (HttpClientErrorException.BadRequest e) {
        // On extrait le message d'erreur renvoyé par le Rule Engine
        throw new PricingException("Erreur de calcul : " + e.getResponseBodyAs(String.class));
    } 
        catch (Exception e) {
            throw new PricingException("Failed to retrieve pricing from rule engine: " + e.getMessage());
        }
          
    }}


    

