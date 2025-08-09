package com.team12.flightmanagement.service;

import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Flight;
import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.FlightRepository;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    public PassengerService(PassengerRepository passengerRepository, FlightRepository flightRepository) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public List<Map<String, Object>> getPassengersWithAirports() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Passenger p : passengers) {
            String fullName = (p.getFirstName() == null ? "" : p.getFirstName()) +
                    " " +
                    (p.getLastName() == null ? "" : p.getLastName());

            Set<String> airportNames = new HashSet<>();
            List<Aircraft> flown = p.getAircraftList() == null ? Collections.emptyList() : p.getAircraftList();

            for (Aircraft ac : flown) {
                List<Flight> flights = flightRepository.findByAircraftId(ac.getId());
                for (Flight f : flights) {
                    Airport from = f.getFromAirport();
                    Airport to = f.getToAirport();
                    if (from != null && from.getName() != null) airportNames.add(from.getName());
                    if (to != null && to.getName() != null) airportNames.add(to.getName());
                }
            }

            Map<String, Object> entry = new HashMap<>();
            entry.put("passenger", fullName.trim());
            entry.put("airports", new ArrayList<>(airportNames));
            result.add(entry);
        }

        return result;
    }
}
