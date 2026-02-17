package com.assurtech.quote_service.infrastructure.adapters.out.db;


import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuoteRepository extends  JpaRepository<QuoteEntity, Long> {
    
}
