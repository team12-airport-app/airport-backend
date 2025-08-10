package com.team12.flightmanagement.service;

import com.team12.flightmanagement.dto.AirportCreateDTO;
import com.team12.flightmanagement.dto.AirportUpdateDTO;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportService(AirportRepository airportRepository, CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    // --- READs ---

    public List<Airport> findAllAirports() {
        return findAll();
    }

    public List<Airport> findAll() {
        return airportRepository.findAll();
    }


    public Airport getById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("airport not found: " + id));
    }

    // --- CREATE ---

    public Airport create(AirportCreateDTO dto) {
        Airport a = new Airport();
        a.setName(dto.name);
        a.setCode(dto.code);

        if (dto.cityId != null) {
            City city = cityRepository.findById(dto.cityId)
                    .orElseThrow(() -> new NoSuchElementException("city not found: " + dto.cityId));
            a.setCity(city);
        }
        return airportRepository.save(a);
    }

    public Airport update(Long id, AirportUpdateDTO dto) {
        Airport a = airportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("airport not found: " + id));

        if (dto.name != null) a.setName(dto.name);
        if (dto.code != null) a.setCode(dto.code);

        if (dto.cityId != null) {
            City city = cityRepository.findById(dto.cityId)
                    .orElseThrow(() -> new NoSuchElementException("city not found: " + dto.cityId));
            a.setCity(city);
        }

        return airportRepository.save(a);
    }

    // DELETE

    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new NoSuchElementException("airport not found: " + id);
        }
        airportRepository.deleteById(id);
    }
}

