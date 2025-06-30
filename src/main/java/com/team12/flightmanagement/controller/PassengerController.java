package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    // listing passengers with plane and airport relationships
    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // return passengers plus the airports they used
    @GetMapping("/passengers-with-airports")
    public List<Map<String, Object>> getPassengersWithAirports() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Passenger p : passengers) {
            Map<String, Object> entry = new HashMap<>();
            String fullName = p.getFirstName() + " " + p.getLastName();
            Set<String> airportNames = new HashSet<>();

            if (p.getAircraftList() != null) {
                for (var aircraft : p.getAircraftList()) {
                    if (aircraft.getAirports() != null) {
                        for (var airport : aircraft.getAirports()) {
                            airportNames.add(airport.getName() + " (" + airport.getCode() + ")");
                        }
                    }
                }
            }

            entry.put("passenger", fullName);
            entry.put("airports", new ArrayList<>(airportNames));
            result.add(entry);
        }

        return result;
    }
}






