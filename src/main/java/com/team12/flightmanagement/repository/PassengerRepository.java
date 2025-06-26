package com.team12.flightmanagement.repository;
import com.team12.flightmanagement.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}


