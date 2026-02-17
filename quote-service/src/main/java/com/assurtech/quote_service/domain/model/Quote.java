package com.assurtech.quote_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Quote(
    Long quoteId,
    String customerName,
    String vehicleType,
    int age,
    int claims,
    BigDecimal monthlyPremium,
    String riskLevel,
    LocalDateTime createdAt
) {
    public Quote {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
