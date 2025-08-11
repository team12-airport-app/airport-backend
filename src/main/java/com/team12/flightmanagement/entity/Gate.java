package com.team12.flightmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gates",
        uniqueConstraints = @UniqueConstraint(name = "uk_gate_airport_code", columnNames = {"airport_id","code"}))
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String code;

    @Column(length = 120)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_gate_airport"))
    private Airport airport;

    @PrePersist
    @PreUpdate
    protected void normalize() {
        if (code != null) code = code.trim().toUpperCase();
        if (description != null) description = description.trim();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Airport getAirport() { return airport; }
    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setAirport(Airport airport) { this.airport = airport; }
}