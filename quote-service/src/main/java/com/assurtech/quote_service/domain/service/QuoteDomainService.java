package com.assurtech.quote_service.domain.service;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.in.CreateQuoteUseCase;

import com.assurtech.quote_service.domain.ports.out.PricingClientPort;
import com.assurtech.quote_service.domain.ports.out.PricingResponse;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;

public class QuoteDomainService implements CreateQuoteUseCase {
    private final QuoteRepositoryPort repositoryPort;
    private final PricingClientPort pricingClientPort;

    public QuoteDomainService(QuoteRepositoryPort repositoryPort, PricingClientPort pricingClientPort) {
        this.repositoryPort = repositoryPort;
        this.pricingClientPort = pricingClientPort;
    }

    @Override
    public Quote create(String name, String car, int age, int claims) {
        PricingResponse pricing = pricingClientPort.getPricing(age, claims, car);
        System.out.println("Received pricing: " + pricing);
        System.out.println("Age: " + age + ", Claims: " + claims + ", Car: " + car);
        
        Quote quote = new Quote(null, name, car, age, claims, pricing.monthlyPremium(), pricing.riskLevel(), null);
        return repositoryPort.save(quote);
    }
    
}
