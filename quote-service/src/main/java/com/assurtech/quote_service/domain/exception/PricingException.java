package com.assurtech.quote_service.domain.exception;

public class PricingException extends RuntimeException {
    public PricingException(String message) {
        super(message);
    }
}
