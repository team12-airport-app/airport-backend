package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.AircraftRepository;
import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.PassengerRepository;
import com.team12.flightmanagement.dto.UpdateAircraftDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftRepository aircraftRepository;
    private final PassengerRepository passengerRepository;
    private final AirportRepository airportRepository;

    public AircraftController(AircraftRepository aircraftRepository,
                              PassengerRepository passengerRepository,
                              AirportRepository airportRepository) {
        this.aircraftRepository = aircraftRepository;
        this.passengerRepository = passengerRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    // CREATE
    @PostMapping
    public Aircraft addAircraft(@RequestBody UpdateAircraftDTO dto) {
        Aircraft aircraft = new Aircraft();
        aircraft.setType(dto.type);
        aircraft.setAirlineName(dto.airlineName);
        aircraft.setNumberOfPassengers(dto.numberOfPassengers);

        // Set passengers by IDs
        if (dto.passengerIds != null && !dto.passengerIds.isEmpty()) {
            List<Passenger> passengers = passengerRepository.findAllById(dto.passengerIds);
            aircraft.setPassengers(passengers);
        } else {
            aircraft.setPassengers(List.of());
        }
        // Set airports by IDs
        if (dto.airportIds != null && !dto.airportIds.isEmpty()) {
            List<Airport> airports = airportRepository.findAllById(dto.airportIds);
            aircraft.setAirports(airports);
        } else {
            aircraft.setAirports(List.of());
        }

        return aircraftRepository.save(aircraft);
    }

    // READ: Get all airports used by an aircraft (by aircraft ID)
    @GetMapping("/{aircraftId}/airports")
    public List<Airport> getAirportsByAircraft(@PathVariable Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));
        return aircraft.getAirports();
    }

    // PUT - update using DTO with IDs
    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable Long id, @RequestBody UpdateAircraftDTO dto) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));
        aircraft.setType(dto.type);
        aircraft.setAirlineName(dto.airlineName);
        aircraft.setNumberOfPassengers(dto.numberOfPassengers);

        // Set passengers by IDs
        if (dto.passengerIds != null && !dto.passengerIds.isEmpty()) {
            List<Passenger> passengers = passengerRepository.findAllById(dto.passengerIds);
            aircraft.setPassengers(passengers);
        } else {
            aircraft.setPassengers(List.of());
        }
        // Set airports by IDs
        if (dto.airportIds != null && !dto.airportIds.isEmpty()) {
            List<Airport> airports = airportRepository.findAllById(dto.airportIds);
            aircraft.setAirports(airports);
        } else {
            aircraft.setAirports(List.of());
        }

        return aircraftRepository.save(aircraft);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable Long id) {
        aircraftRepository.deleteById(id);
    }

    // The following are for troubleshooting

    // Test: Update just the type
    @PutMapping("/test/{id}")
    public String updateAircraftTest(@PathVariable Long id, @RequestBody Map<String, Object> map) {
        return "Received keys: " + map.keySet();
    }

    // Map-based test endpoint
    @PutMapping("/testmap/{id}")
    public String updateWithMap(@PathVariable Long id, @RequestBody Map<String, Object> map) {
        return "Received keys: " + map.keySet();
    }
}

