package com.team12.flightmanagement.repository;

import com.team12.flightmanagement.model.Airport;
import com.team12.flightmanagement.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, Long> {
    boolean existsByAirportAndCodeIgnoreCase(Airport airport, String code);
    Optional<Gate> findByAirport_CodeIgnoreCaseAndCodeIgnoreCase(String airportCode, String code);
    List<Gate> findByAirport_CodeIgnoreCaseOrderByCodeAsc(String airportCode);
}