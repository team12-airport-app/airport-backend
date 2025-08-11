package com.team12.flightmanagement.repository;

import com.team12.flightmanagement.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    boolean existsByCodeIgnoreCase(String code);
    Optional<Airline> findByCode(String code);
}