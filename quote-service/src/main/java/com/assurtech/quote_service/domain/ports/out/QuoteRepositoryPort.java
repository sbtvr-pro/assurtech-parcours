package com.assurtech.quote_service.domain.ports.out;

import java.util.List;

import com.assurtech.quote_service.domain.model.Quote;

public interface QuoteRepositoryPort {
    Quote save(Quote quote);
    List<Quote> findAll();
}
