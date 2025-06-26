package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.Airport;
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

    // Update a city
    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));

        city.setName(cityDetails.getName());
        city.setProvince(cityDetails.getProvince());
        city.setPopulation(cityDetails.getPopulation());
        return cityRepository.save(city);
    }

    // Delete a city
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityRepository.deleteById(id);
    }

}

