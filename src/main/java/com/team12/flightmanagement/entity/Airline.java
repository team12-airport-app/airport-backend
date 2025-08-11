package com.team12.flightmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "airlines",
        uniqueConstraints = @UniqueConstraint(name = "uk_airline_code", columnNames = {"code"}))
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8, unique = true)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @PrePersist
    @PreUpdate
    protected void normalize() {
        if (code != null) code = code.trim().toUpperCase();
        if (name != null) name = name.trim();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
}