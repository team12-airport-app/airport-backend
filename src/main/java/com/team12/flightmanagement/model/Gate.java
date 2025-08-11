package com.team12.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "gates",
        uniqueConstraints = @UniqueConstraint(name = "uk_gate_airport_code", columnNames = {"airport_id", "code"}))
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // note to self - gate codes unique within airport
    @NotBlank
    @Size(max = 8)
    @Column(nullable = false, length = 8)
    private String code;

    @Size(max = 120)
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

    // getters setters
    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Airport getAirport() { return airport; }
    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setAirport(Airport airport) { this.airport = airport; }
}