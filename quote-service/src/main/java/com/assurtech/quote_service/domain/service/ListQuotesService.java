package com.assurtech.quote_service.domain.service;

import java.util.List;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.in.ListQuotesUseCase;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;

public class ListQuotesService implements ListQuotesUseCase 
{

    private final QuoteRepositoryPort quoteRepository;

    public ListQuotesService(QuoteRepositoryPort quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public List<Quote> execute() {
       return quoteRepository.findAll();
    }

    
}
