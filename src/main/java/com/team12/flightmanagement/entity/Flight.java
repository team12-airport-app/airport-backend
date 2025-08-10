package com.team12.flightmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aircraft_id")
    @JsonBackReference
    private Aircraft aircraft;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_airport_id")
    private Airport fromAirport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_airport_id")
    private Airport toAirport;

    private LocalDateTime departedAt;
    private LocalDateTime arrivedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

    public Airport getFromAirport() { return fromAirport; }
    public void setFromAirport(Airport fromAirport) { this.fromAirport = fromAirport; }

    public Airport getToAirport() { return toAirport; }
    public void setToAirport(Airport toAirport) { this.toAirport = toAirport; }

    public LocalDateTime getDepartedAt() { return departedAt; }
    public void setDepartedAt(LocalDateTime departedAt) { this.departedAt = departedAt; }

    public LocalDateTime getArrivedAt() { return arrivedAt; }
    public void setArrivedAt(LocalDateTime arrivedAt) { this.arrivedAt = arrivedAt; }
}
