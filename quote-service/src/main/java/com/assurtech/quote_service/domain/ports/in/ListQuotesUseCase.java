package com.assurtech.quote_service.domain.ports.in;

import java.util.List;

import com.assurtech.quote_service.domain.model.Quote;

public interface ListQuotesUseCase {
    List<Quote> execute();
}
