package com.assurtech.rule_engine_service.domain.model;

public record DriverProfile(
        String driverId,
        String name,
        int age,
        int yearsOfExperience,
        String licenseType,
        String vehicleType,
        int claimCount
) {
public DriverProfile {
        if (age < 18) throw new IllegalArgumentException("L'âge doit être supérieur à 18 ans.");
    }

}