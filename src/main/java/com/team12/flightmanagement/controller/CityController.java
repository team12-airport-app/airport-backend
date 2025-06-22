package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    // Get airports for a given city by city ID
    @GetMapping("/{cityId}/airports")
    public List<Airport> getAirportsByCity(@PathVariable Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return city.getAirports();
    }
}

