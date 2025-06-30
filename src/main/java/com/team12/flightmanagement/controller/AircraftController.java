package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    // return aircraft for CLI
    @GetMapping("/aircraft")
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    // aircraft + passenger names
    @GetMapping("/aircraft-with-passengers")
    public List<Map<String, Object>> getAircraftWithPassengerNames() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Aircraft ac : aircraftList) {
            Map<String, Object> acMap = new HashMap<>();
            acMap.put("id", ac.getId());
            acMap.put("type", ac.getType());
            acMap.put("airlineName", ac.getAirlineName());

            List<String> passengerNames = new ArrayList<>();
            for (Passenger p : ac.getPassengers()) {
                passengerNames.add(p.getFirstName() + " " + p.getLastName());
            }
            acMap.put("passengers", passengerNames);
            acMap.put("numberOfPassengers", passengerNames.size());

            result.add(acMap);
        }

        return result;
    }

    // for option 3 in CLI
    @GetMapping("/aircraft-with-airports")
    public List<Map<String, Object>> getAircraftWithAirports() {
        List<Aircraft> allAircraft = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Aircraft ac : allAircraft) {
            Map<String, Object> map = new HashMap<>();
            map.put("aircraft", ac.getType() + " (" + ac.getAirlineName() + ")");
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



