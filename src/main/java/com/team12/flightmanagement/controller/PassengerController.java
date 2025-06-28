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

    @GetMapping("/passengers")
    public List<Map<String, Object>> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Passenger p : passengers) {
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("id", p.getId());
            pMap.put("firstName", p.getFirstName());
            pMap.put("lastName", p.getLastName());
            pMap.put("phoneNumber", p.getPhoneNumber());
            if (p.getCity() != null) {
                pMap.put("cityId", p.getCity().getId());
                pMap.put("cityName", p.getCity().getName());
            }
            // Optionally add aircraft IDs if needed for your CLI
            result.add(pMap);
        }
        return result;
    }
}




