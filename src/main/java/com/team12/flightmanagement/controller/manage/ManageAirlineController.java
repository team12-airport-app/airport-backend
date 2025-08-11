package com.team12.flightmanagement.controller.manage;

import com.team12.flightmanagement.dto.airline.AirlineDto;
import com.team12.flightmanagement.dto.airline.CreateAirlineRequest;
import com.team12.flightmanagement.dto.airline.UpdateAirlineRequest;
import com.team12.flightmanagement.model.Airline;
import com.team12.flightmanagement.repository.AirlineRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/manage/airlines")
public class ManageAirlineController {

    private final AirlineRepository airlineRepository;

    public ManageAirlineController(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @GetMapping
    public List<AirlineDto> list() {
        return airlineRepository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public AirlineDto get(@PathVariable Long id) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        return toDto(a);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AirlineDto create(@Valid @RequestBody CreateAirlineRequest req) {
        if (airlineRepository.existsByCodeIgnoreCase(req.code())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Airline code already exists");
        }
        Airline a = new Airline();
        a.setCode(req.code());
        a.setName(req.name());
        a = airlineRepository.save(a);
        return toDto(a);
    }

    @PutMapping("/{id}")
    public AirlineDto update(@PathVariable Long id, @Valid @RequestBody UpdateAirlineRequest req) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        if (!a.getCode().equalsIgnoreCase(req.code())
                && airlineRepository.existsByCodeIgnoreCase(req.code())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Airline code already exists");
        }
        a.setCode(req.code());
        a.setName(req.name());
        a = airlineRepository.save(a);
        return toDto(a);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found");
        }
        airlineRepository.deleteById(id);
    }

    private AirlineDto toDto(Airline a) {
        return new AirlineDto(a.getId(), a.getCode(), a.getName());
    }
}