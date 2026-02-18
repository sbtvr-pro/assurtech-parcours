package com.assurtech.quote_service.domain.service;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.in.DeleteQuotesUseCase;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;

public class DeletQuotesService implements DeleteQuotesUseCase {

      private final QuoteRepositoryPort quoteRepository;
    public DeletQuotesService(QuoteRepositoryPort quoteRepository) {
        this.quoteRepository = quoteRepository;
    }
    @Override
    public void deleteAll() {
        quoteRepository.deleteAll();
    }

    @Override
    public Quote deleteById(Long id) {
        return quoteRepository.deleteById(id);
    }
    
}
