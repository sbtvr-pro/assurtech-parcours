package com.assurtech.quote_service.infrastructure.adapters.out.db;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "QUOTES") // Doit correspondre au nom dans ton changelog Liquibase
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "MONTHLY_PREMIUM")
    private BigDecimal monthlyPremium;

    @Column(name = "RISK_LEVEL")
    private String riskLevel;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    // --- CONSTRUCTEURS ---

    // Obligatoire pour JPA/Hibernate
    public QuoteEntity() {
    }

    public QuoteEntity(Long id, String customerName, String carType, BigDecimal monthlyPremium, String riskLevel, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.vehicleType = carType;
        this.monthlyPremium = monthlyPremium;
        this.riskLevel = riskLevel;
        this.createdAt = createdAt;
    }

    // --- GETTERS ET SETTERS ---
    // Indispensables sans Lombok pour que Hibernate puisse lire/écrire

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public BigDecimal getMonthlyPremium() { return monthlyPremium; }
    public void setMonthlyPremium(BigDecimal monthlyPremium) { this.monthlyPremium = monthlyPremium; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
 
    
}
