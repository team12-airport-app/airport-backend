package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.service.PassengerService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // list all passengers
    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    // return passengers + airports they used
    @GetMapping("/passengers-with-airports")
    public List<Map<String, Object>> getPassengersWithAirports() {
        return passengerService.getPassengersWithAirports();
    }
}







