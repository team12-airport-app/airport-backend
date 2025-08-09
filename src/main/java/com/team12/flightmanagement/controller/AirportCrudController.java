package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.dto.AirportCreateDTO;
import com.team12.flightmanagement.dto.AirportUpdateDTO;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.service.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/manage/airports")
public class AirportCrudController {

    private final AirportService airportService;

    public AirportCrudController(AirportService airportService) {
        this.airportService = airportService;
    }

    // create an airport
    @PostMapping
    public ResponseEntity<Airport> create(@RequestBody AirportCreateDTO dto) {
        Airport saved = airportService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // list all
    @GetMapping
    public List<Airport> list() {
        return airportService.findAll();
    }

    // get
    @GetMapping("/{id}")
    public Airport get(@PathVariable Long id) {
        return airportService.getById(id);
    }

    // update
    @PutMapping("/{id}")
    public Airport update(@PathVariable Long id, @RequestBody AirportUpdateDTO dto) {
        return airportService.update(id, dto);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 404
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
