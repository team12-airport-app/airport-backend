package com.team12.flightmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team12.flightmanagement.entity.Aircraft;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByAirlineName(String airlineName);
}
