package com.assurtech.rule_engine_service.domain.service;

import java.math.BigDecimal;

import com.assurtech.rule_engine_service.domain.model.DriverProfile;
import com.assurtech.rule_engine_service.domain.model.PriceQuote;
import com.assurtech.rule_engine_service.domain.ports.in.CalculatePriceUseCase;

public class PricingService implements CalculatePriceUseCase{

    @Override
    public PriceQuote calculatePrice(DriverProfile profile) {
        // Implémentation de la logique de tarification basée sur le profil du conducteur
        // Par exemple, on peut utiliser des règles simples pour déterminer le niveau de risque et le prix
     // 1. Détermination de la prime de base selon le type de voiture (Switch Java 21)
        BigDecimal currentPremium = switch (profile.vehicleType().toUpperCase()) {
            case "SPORT" -> new BigDecimal("120.00");
            case "SUV"   -> new BigDecimal("90.00");
            case "SEDAN" -> new BigDecimal("70.00");
            default      -> new BigDecimal("60.00");
        };
    // 2. Initialisation du niveau de risque par défaut
        String riskLevel = "LOW";

    if (profile.age() < 25) {
            currentPremium = currentPremium.add(new BigDecimal("50.00"));
            riskLevel = "HIGH";
        }
        // 4. Ajustement selon l'expérience (licenseYears)
        else if (profile.yearsOfExperience() < 5) {
            currentPremium = currentPremium.add(new BigDecimal("30.00"));
            if (!riskLevel.equals("HIGH")) riskLevel = "MEDIUM";
        }
        // 5. Application des sinistres (Claim Count)
        if (profile.claimCount() > 0) {
            // On ajoute 20€ par sinistre + on multiplie par 1.5 (votre logique précédente)
            BigDecimal claimSurcharge = new BigDecimal("20.00")
                    .multiply(new BigDecimal(profile.claimCount()));
            
            currentPremium = currentPremium.add(claimSurcharge).multiply(new BigDecimal("1.5"));
            riskLevel = "HIGH";
        }

        return new PriceQuote(currentPremium, riskLevel);
    }
}
