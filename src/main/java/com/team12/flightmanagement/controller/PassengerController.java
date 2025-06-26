package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;


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

    // Get all aircraft flown by a passenger (by passenger ID)
    @GetMapping("/{passengerId}/aircraft")
    public List<Aircraft> getAircraftByPassenger(@PathVariable Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        return passenger.getAircraftList();
    }

    // Get all airports a passenger has used (by their flights)
    @GetMapping("/{passengerId}/airports")
    public List<Airport> getAirportsUsedByPassenger(@PathVariable Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        // Use streams to flatten all airports from all aircraft flown by the passenger
        return passenger.getAircraftList().stream()
                .flatMap(aircraft -> aircraft.getAirports().stream())
                .distinct()
                .toList();
    }

    // Update a passenger
    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        passenger.setFirstName(passengerDetails.getFirstName());
        passenger.setLastName(passengerDetails.getLastName());
        passenger.setPhoneNumber(passengerDetails.getPhoneNumber());
        passenger.setCity(passengerDetails.getCity());
        passenger.setAircraftList(passengerDetails.getAircraftList());
        return passengerRepository.save(passenger);
    }

    // Delete a passenger
    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerRepository.deleteById(id);
    }

}

