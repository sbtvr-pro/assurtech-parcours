package com.assurtech.quote_service.domain.ports.in;


import com.assurtech.quote_service.domain.model.Quote;

public interface CreateQuoteUseCase {
    Quote create(String name, String car, int age, int claims);
}
