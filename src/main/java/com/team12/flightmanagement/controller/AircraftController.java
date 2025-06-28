package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    // Endpoint: List all aircraft
    @GetMapping("/aircraft")
    public List<Map<String, Object>> getAllAircraft() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Aircraft ac : aircraftList) {
            Map<String, Object> acMap = new HashMap<>();
            acMap.put("id", ac.getId());
            acMap.put("type", ac.getType());
            acMap.put("airlineName", ac.getAirlineName());
            acMap.put("numberOfPassengers", ac.getNumberOfPassengers());
            // Add airports if you want them here as well (optional)
            result.add(acMap);
        }
        return result;
    }

    // Option 3 Endpoint: For CLI "What airports do aircraft take off from and land at?"
    @GetMapping("/aircraft-with-airports")
    public List<Map<String, Object>> getAircraftWithAirports() {
        List<Aircraft> allAircraft = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Aircraft ac : allAircraft) {
            Map<String, Object> map = new HashMap<>();
            // Aircraft label: type and airline
            map.put("aircraft", ac.getType() + " (" + ac.getAirlineName() + ")");
            // List of airport names/codes
            List<String> airportNames = new ArrayList<>();
            for (Airport ap : ac.getAirports()) {
                airportNames.add(ap.getName() + " (" + ap.getCode() + ")");
            }
            map.put("airports", airportNames);
            result.add(map);
        }
        return result;
    }
}

