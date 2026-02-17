package com.assurtech.rule_engine_service.infrastructure.adapters.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assurtech.rule_engine_service.domain.model.DriverProfile;
import com.assurtech.rule_engine_service.domain.model.PriceQuote;
import com.assurtech.rule_engine_service.domain.ports.in.CalculatePriceUseCase;

@RestController
@RequestMapping("/api/rules")
public class PricingController {

    private final CalculatePriceUseCase calculatePriceUseCase;

    // Constructeur explicite (Injection Spring)
    public PricingController(CalculatePriceUseCase calculatePriceUseCase) {
        this.calculatePriceUseCase = calculatePriceUseCase;
    }
    @PostMapping("/calculate")
    public PriceQuote calculate(@RequestBody DriverProfile profile) {
        return calculatePriceUseCase.calculatePrice(profile);
    }
}
