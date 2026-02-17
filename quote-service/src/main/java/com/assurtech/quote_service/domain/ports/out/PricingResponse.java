package com.assurtech.quote_service.domain.ports.out;

import java.math.BigDecimal;

public record PricingResponse(
    BigDecimal monthlyPremium,
    String riskLevel
) {}
