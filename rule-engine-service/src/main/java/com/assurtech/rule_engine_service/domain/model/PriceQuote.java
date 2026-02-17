package com.assurtech.rule_engine_service.domain.model;

import java.math.BigDecimal;

public record PriceQuote(
    BigDecimal monthlyPremium,
    String riskLevel
) {
    
}
