package com.team12.flightmanagement.repository;

import com.team12.flightmanagement.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAircraft_Id(Long aircraftId);
    List<Flight> findByAircraftId(Long aircraftId);
}
