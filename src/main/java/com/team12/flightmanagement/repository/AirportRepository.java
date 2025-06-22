package com.team12.flightmanagement.repository;

import com.team12.flightmanagement.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository interface for Airport entity
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
}
