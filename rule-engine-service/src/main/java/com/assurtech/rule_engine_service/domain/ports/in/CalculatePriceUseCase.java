package com.assurtech.rule_engine_service.domain.ports.in;

import com.assurtech.rule_engine_service.domain.model.DriverProfile;
import com.assurtech.rule_engine_service.domain.model.PriceQuote;

public interface CalculatePriceUseCase {
    PriceQuote calculatePrice(DriverProfile driverProfile);
    
}
