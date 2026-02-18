package com.assurtech.quote_service.domain.ports.in;

import com.assurtech.quote_service.domain.model.Quote;

public interface DeleteQuotesUseCase {
    void deleteAll();

    Quote deleteById(Long id);
}
