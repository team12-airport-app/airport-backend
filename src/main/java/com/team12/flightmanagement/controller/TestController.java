package com.team12.flightmanagement.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PutMapping("/test")
    public String test(@RequestBody SimpleAircraft aircraft) {
        return aircraft.getType();
    }
}



