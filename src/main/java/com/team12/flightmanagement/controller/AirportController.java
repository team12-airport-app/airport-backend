package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.service.AirportService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // list airports
    @GetMapping("/airports")
    public List<Map<String, Object>> getAllAirports() {
        List<Airport> airports = airportService.findAllAirports();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Airport airport : airports) {
            Map<String, Object> airportMap = new HashMap<>();
            airportMap.put("id", airport.getId());
            airportMap.put("name", airport.getName());
            airportMap.put("code", airport.getCode());
            if (airport.getCity() != null) {
                airportMap.put("cityId", airport.getCity().getId());
                airportMap.put("cityName", airport.getCity().getName());
            }
            result.add(airportMap);
        }
        return result;
    }
}





