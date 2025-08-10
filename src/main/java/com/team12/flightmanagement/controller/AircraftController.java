package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.service.AircraftService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // list aircraft
    @GetMapping("/aircraft")
    public List<Aircraft> getAllAircraft() {
        return aircraftService.getAllAircraft();
    }

    // aircraft + passenger names
    @GetMapping("/aircraft-with-passengers")
    public List<Map<String, Object>> getAircraftWithPassengerNames() {
        return aircraftService.getAircraftWithPassengerNames();
    }

    // aircraft + unique airports used across flights
    @GetMapping("/aircraft-with-airports")
    public List<Map<String, Object>> getAircraftWithAirports() {
        return aircraftService.getAircraftWithAirports();
    }

    // list flights for an aircraft
    @GetMapping("/aircraft/{id}/flights")
    public List<Map<String, Object>> getFlightsForAircraft(@PathVariable Long id) {
        return aircraftService.getFlightsForAircraft(id);
    }
}





