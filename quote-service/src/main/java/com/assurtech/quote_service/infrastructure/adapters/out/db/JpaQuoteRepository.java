package com.assurtech.quote_service.infrastructure.adapters.out.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuoteRepository extends  JpaRepository<QuoteEntity, UUID> {
    
}
