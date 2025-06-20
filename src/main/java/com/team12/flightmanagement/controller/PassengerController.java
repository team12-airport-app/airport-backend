package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// REST controller for Passenger endpoints.
@RestController
@RequestMapping("/passengers")
public class PassengerController {
    @Autowired
    private PassengerRepository passengerRepository;

    // Get all passengers
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Add a new passenger
    @PostMapping
    public Passenger addPassenger(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}

