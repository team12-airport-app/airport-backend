package com.team12.flightmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team12.flightmanagement.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {}



