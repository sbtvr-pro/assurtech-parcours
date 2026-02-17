package com.assurtech.quote_service.infrastructure.adapters.out.db;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;
import org.springframework.stereotype.Component;

@Component // <-- C'est ce qui permet à Spring de "trouver" le Bean
public class JpaQuoteRepositoryAdapter implements QuoteRepositoryPort {

    private final JpaQuoteRepository repository;

    public JpaQuoteRepositoryAdapter(JpaQuoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quote save(Quote quote) {
        // Ici tu convertis ton Record Quote en QuoteEntity
        QuoteEntity entity = new QuoteEntity(
            quote.quoteId(),
            quote.customerName(),
            quote.vehicleType(),
            quote.monthlyPremium(),
            quote.riskLevel(),
            quote.createdAt()
        );
        
        QuoteEntity savedEntity = repository.save(entity);
        
        // Puis tu reconvertis l'entité sauvegardée en objet du domaine (Record)
        return new Quote(
            savedEntity.getId(),
            savedEntity.getCustomerName(),
            savedEntity.getVehicleType(),
            0, // age (si non stocké)
            0, // claims (si non stocké)
            savedEntity.getMonthlyPremium(),
            savedEntity.getRiskLevel(),
            savedEntity.getCreatedAt()
        );
    }
}