package com.assurtech.quote_service.domain.ports.out;

import com.assurtech.quote_service.domain.model.Quote;

public interface QuoteRepositoryPort {
    Quote save(Quote quote);
}
