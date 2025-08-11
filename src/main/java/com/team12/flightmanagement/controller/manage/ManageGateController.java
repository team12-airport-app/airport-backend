package com.team12.flightmanagement.controller.manage;

import com.team12.flightmanagement.dto.gate.CreateGateRequest;
import com.team12.flightmanagement.dto.gate.GateDTO;
import com.team12.flightmanagement.dto.gate.UpdateGateRequest;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.Gate;
import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.GateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/manage/gates")
public class ManageGateController {

    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    public ManageGateController(GateRepository gateRepository, AirportRepository airportRepository) {
        this.gateRepository = gateRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public List<GateDTO> list(@RequestParam(value = "airport", required = false) String airportCode) {
        if (airportCode == null || airportCode.isBlank()) {
            return gateRepository.findAll().stream()
                    .sorted(Comparator.comparing(Gate::getCode))
                    .map(this::toDto).toList();
        }
        return gateRepository.findByAirport_CodeIgnoreCaseOrderByCodeAsc(airportCode).stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public GateDTO get(@PathVariable Long id) {
        Gate g = gateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gate not found"));
        return toDto(g);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GateDTO create(@RequestBody CreateGateRequest req) {
        Airport airport = resolveAirport(req.airportId, req.airportCode);
        if (req.code == null || req.code.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code is required");
        }
        if (gateRepository.existsByAirport_CodeIgnoreCaseAndCodeIgnoreCase(airport.getCode(), req.code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Gate code already exists for this airport");
        }
        Gate g = new Gate();
        g.setAirport(airport);
        g.setCode(req.code);
        g.setDescription(req.description);
        g = gateRepository.save(g);
        return toDto(g);
    }

    @PutMapping("/{id}")
    public GateDTO update(@PathVariable Long id, @RequestBody UpdateGateRequest req) {
        Gate g = gateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gate not found"));

        Airport airport = resolveAirport(req.airportId, req.airportCode);
        String newCode = (req.code != null && !req.code.isBlank()) ? req.code : g.getCode();

        boolean codeChangedOrAirportChanged =
                !g.getCode().equalsIgnoreCase(newCode) || !g.getAirport().getId().equals(airport.getId());

        if (codeChangedOrAirportChanged &&
                gateRepository.existsByAirport_CodeIgnoreCaseAndCodeIgnoreCase(airport.getCode(), newCode)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Gate code already exists for this airport");
        }

        g.setAirport(airport);
        g.setCode(newCode);
        g.setDescription(req.description);
        g = gateRepository.save(g);
        return toDto(g);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!gateRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gate not found");
        }
        gateRepository.deleteById(id);
    }

    private Airport resolveAirport(Long airportId, String airportCode) {
        if (airportId != null) {
            return airportRepository.findById(airportId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown airportId"));
        }
        if (airportCode != null && !airportCode.isBlank()) {
            // Avoid assuming custom repo signatures; fall back to findAll() + match by code
            return airportRepository.findAll().stream()
                    .filter(a -> a.getCode() != null && a.getCode().equalsIgnoreCase(airportCode))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown airportCode"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide airportId or airportCode");
    }

    private GateDTO toDto(Gate g) {
        GateDTO dto = new GateDTO();
        dto.id = g.getId();
        dto.code = g.getCode();
        dto.description = g.getDescription();
        dto.airportCode = g.getAirport() != null ? g.getAirport().getCode() : null;
        return dto;
    }
}
