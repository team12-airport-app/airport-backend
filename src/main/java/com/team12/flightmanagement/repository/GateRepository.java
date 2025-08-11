package com.team12.flightmanagement.repository;

import com.team12.flightmanagement.entity.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, Long> {
    boolean existsByAirport_CodeIgnoreCaseAndCodeIgnoreCase(String airportCode, String code);
    Optional<Gate> findByAirport_CodeIgnoreCaseAndCodeIgnoreCase(String airportCode, String code);
    List<Gate> findByAirport_CodeIgnoreCaseOrderByCodeAsc(String airportCode);
}