package com.team12.flightmanagement.controller.manage;

import com.team12.flightmanagement.dto.airline.AirlineDTO;
import com.team12.flightmanagement.dto.airline.CreateAirlineRequest;
import com.team12.flightmanagement.dto.airline.UpdateAirlineRequest;
import com.team12.flightmanagement.entity.Airline;
import com.team12.flightmanagement.repository.AirlineRepository;
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
    public List<AirlineDTO> list() {
        return airlineRepository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public AirlineDTO get(@PathVariable Long id) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        return toDto(a);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AirlineDTO create(@RequestBody CreateAirlineRequest req) {
        if (req == null || req.code == null || req.code.isBlank() || req.name == null || req.name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code and name are required");
        }
        if (airlineRepository.existsByCodeIgnoreCase(req.code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Airline code already exists");
        }
        Airline a = new Airline();
        a.setCode(req.code);
        a.setName(req.name);
        a = airlineRepository.save(a);
        return toDto(a);
    }

    @PutMapping("/{id}")
    public AirlineDTO update(@PathVariable Long id, @RequestBody UpdateAirlineRequest req) {
        if (req == null || req.code == null || req.code.isBlank() || req.name == null || req.name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code and name are required");
        }
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        if (!a.getCode().equalsIgnoreCase(req.code) && airlineRepository.existsByCodeIgnoreCase(req.code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Airline code already exists");
        }
        a.setCode(req.code);
        a.setName(req.name);
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

    private AirlineDTO toDto(Airline a) {
        AirlineDTO dto = new AirlineDTO();
        dto.id = a.getId();
        dto.code = a.getCode();
        dto.name = a.getName();
        return dto;
    }
}