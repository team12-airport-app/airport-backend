package com.team12.flightmanagement.service;

import com.team12.flightmanagement.entity.Aircraft;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.Flight;
import com.team12.flightmanagement.entity.Passenger;
import com.team12.flightmanagement.repository.AircraftRepository;
import com.team12.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;

    public AircraftService(AircraftRepository aircraftRepository, FlightRepository flightRepository) {
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
    }

    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    public List<Map<String, Object>> getAircraftWithPassengerNames() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Aircraft ac : aircraftList) {
            Map<String, Object> acMap = new HashMap<>();
            acMap.put("id", ac.getId());
            acMap.put("type", ac.getType());
            acMap.put("airlineName", ac.getAirlineName());

            List<String> passengerNames = new ArrayList<>();
            List<Passenger> passengers = ac.getPassengers() == null ? Collections.emptyList() : ac.getPassengers();
            for (Passenger p : passengers) {
                String name = ((p.getFirstName() == null ? "" : p.getFirstName()) + " " +
                        (p.getLastName() == null ? "" : p.getLastName())).trim();
                if (!name.isEmpty()) passengerNames.add(name);
            }
            acMap.put("passengers", passengerNames);
            result.add(acMap);
        }
        return result;
    }

    public List<Map<String, Object>> getAircraftWithAirports() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Aircraft ac : aircraftList) {
            Map<String, Object> acMap = new HashMap<>();
            acMap.put("id", ac.getId());
            acMap.put("type", ac.getType());
            acMap.put("airlineName", ac.getAirlineName());

            // airport codes across flights for this aircraft
            Set<String> airportCodes = new LinkedHashSet<>();
            List<Flight> flights = flightRepository.findByAircraftId(ac.getId());
            for (Flight f : flights) {
                Airport from = f.getFromAirport();
                Airport to = f.getToAirport();
                if (from != null && from.getCode() != null) airportCodes.add(from.getCode());
                if (to != null && to.getCode() != null) airportCodes.add(to.getCode());
            }
            acMap.put("airports", new ArrayList<>(airportCodes));
            result.add(acMap);
        }
        return result;
    }

    public List<Map<String, Object>> getFlightsForAircraft(Long aircraftId) {
        List<Flight> flights = flightRepository.findByAircraftId(aircraftId);
        List<Map<String, Object>> out = new ArrayList<>();

        for (Flight f : flights) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", f.getId());
            m.put("aircraftId", f.getAircraft() != null ? f.getAircraft().getId() : null);
            m.put("fromAirportId", f.getFromAirport() != null ? f.getFromAirport().getId() : null);
            m.put("fromAirportCode", f.getFromAirport() != null ? f.getFromAirport().getCode() : null);
            m.put("toAirportId", f.getToAirport() != null ? f.getToAirport().getId() : null);
            m.put("toAirportCode", f.getToAirport() != null ? f.getToAirport().getCode() : null);
            m.put("departedAt", f.getDepartedAt());
            m.put("arrivedAt", f.getArrivedAt());
            out.add(m);
        }
        return out;
    }
}
