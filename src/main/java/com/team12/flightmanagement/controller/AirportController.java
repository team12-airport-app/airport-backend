package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.repository.AirportRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.team12.flightmanagement.dto.UpdateAircraftDTO;


@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportRepository airportRepository;

    public AirportController(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @PostMapping
    public Airport addAirport(@RequestBody Airport airport) {
        return airportRepository.save(airport);
    }

    // Update an airport
    @PutMapping("/{id}")
    public Airport updateAirport(@PathVariable Long id, @RequestBody Airport airportDetails) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found"));
        airport.setName(airportDetails.getName());
        airport.setCode(airportDetails.getCode());
        airport.setCity(airportDetails.getCity());
        return airportRepository.save(airport);
    }

    // Delete an airport
    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable Long id) {
        airportRepository.deleteById(id);
    }

    @PutMapping("/dto/{id}")
    public String updateWithDTO(@PathVariable Long id, @RequestBody UpdateAircraftDTO dto) {
        return "Type: " + dto.type + ", Airline: " + dto.airlineName + ", Passengers: " + dto.passengerIds + ", Airports: " + dto.airportIds;
    }


}

