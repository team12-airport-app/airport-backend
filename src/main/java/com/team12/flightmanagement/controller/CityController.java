package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // list cities and that city's airports
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }
}





