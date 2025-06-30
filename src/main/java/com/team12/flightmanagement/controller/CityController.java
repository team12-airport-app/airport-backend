package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    // listing cities and the airports
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}




