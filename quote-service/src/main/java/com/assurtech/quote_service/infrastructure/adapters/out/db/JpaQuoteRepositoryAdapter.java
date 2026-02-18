package com.assurtech.quote_service.infrastructure.adapters.out.db;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.out.QuoteRepositoryPort;

import java.util.List;

import org.springframework.stereotype.Component;

@Component // Cette annotation indique que cette classe est un composant Spring, ce qui permet à Spring de la détecter et de l'injecter là où elle est nécessaire.
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
            quote.age(),
            quote.claims(),
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
            savedEntity.getCustomerAge(),
            savedEntity.getNumberOfClaims(),
            savedEntity.getMonthlyPremium(),
            savedEntity.getRiskLevel(),
            savedEntity.getCreatedAt()
        );
    }

    @Override
    public List<Quote> findAll() {
        List<QuoteEntity> entities = repository.findAll();
        return entities.stream().map(e -> new Quote(
            e.getId(),
            e.getCustomerName(),
            e.getVehicleType(),
            e.getCustomerAge(),
            e.getNumberOfClaims(),
            e.getMonthlyPremium(),
            e.getRiskLevel(),
            e.getCreatedAt()
        )).toList();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Quote deleteById(Long id) {
        QuoteEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Quote not found"));
        repository.deleteById(id);
        return new Quote(
            entity.getId(),
            entity.getCustomerName(),
            entity.getVehicleType(),
            entity.getCustomerAge(),
            entity.getNumberOfClaims(),
            entity.getMonthlyPremium(),
            entity.getRiskLevel(),
            entity.getCreatedAt()
        );
    }
}