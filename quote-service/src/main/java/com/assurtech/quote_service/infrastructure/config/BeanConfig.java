package com.assurtech.quote_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assurtech.quote_service.domain.ports.in.CreateQuoteUseCase;
import com.assurtech.quote_service.domain.ports.out.PricingClientPort;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;
import com.assurtech.quote_service.domain.service.QuoteDomainService;

@Configuration
public class BeanConfig {
    @Bean
    public CreateQuoteUseCase createQuoteUseCase(QuoteRepositoryPort rep, PricingClientPort prc) {
        return new QuoteDomainService(rep, prc);
      }
}
