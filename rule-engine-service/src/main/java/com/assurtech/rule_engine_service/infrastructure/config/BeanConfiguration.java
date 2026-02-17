package com.assurtech.rule_engine_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assurtech.rule_engine_service.domain.ports.in.CalculatePriceUseCase;
import com.assurtech.rule_engine_service.domain.service.PricingService;

@Configuration
public class BeanConfiguration {
    @Bean
    public CalculatePriceUseCase calculatePriceUseCase() {
        return new PricingService();
    }
}
