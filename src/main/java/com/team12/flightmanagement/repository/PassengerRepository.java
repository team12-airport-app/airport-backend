package com.team12.flightmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team12.flightmanagement.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}



