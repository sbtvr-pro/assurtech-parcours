package com.assurtech.quote_service.domain.ports.out;



public interface PricingClientPort {
    PricingResponse getPricing(int age, int claims, String carType);
}
