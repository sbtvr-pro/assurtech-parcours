package com.assurtech.quote_service.infrastructure.adapters.out.db;

import java.util.List;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;

public class OracleQuoteAdapter implements QuoteRepositoryPort {
   private final JpaQuoteRepository repo;

    public OracleQuoteAdapter(JpaQuoteRepository repo) { this.repo = repo; }

    @Override
    public Quote save(Quote q) {
        QuoteEntity entity = new QuoteEntity(q.quoteId(), q.customerName(), q.vehicleType(),q.age(), q.claims(), q.monthlyPremium(), q.riskLevel(), q.createdAt());
        repo.save(entity);
        return q;
    }

    @Override
    public List<Quote> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
