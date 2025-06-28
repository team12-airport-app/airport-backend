package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/cities")
    public List<Map<String, Object>> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (City city : cities) {
            Map<String, Object> cityMap = new HashMap<>();
            cityMap.put("id", city.getId());
            cityMap.put("name", city.getName());
            cityMap.put("province", city.getProvince());
            cityMap.put("population", city.getPopulation());

            // Only flat airport list, no city in airport, no aircraft/passengers
            List<Map<String, Object>> airports = new ArrayList<>();
            if (city.getAirports() != null) {
                for (Airport airport : city.getAirports()) {
                    Map<String, Object> airportMap = new HashMap<>();
                    airportMap.put("id", airport.getId());
                    airportMap.put("name", airport.getName());
                    airportMap.put("code", airport.getCode());
                    airports.add(airportMap);
                }
            }
            cityMap.put("airports", airports);

            result.add(cityMap);
        }
        return result;
    }
}




