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

    // get all airports
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    // get airport by id
    public Airport getById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("airport not found: " + id));
    }

    // create new airport
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

    // update airport
    public Airport update(Long id, AirportUpdateDTO dto) {
        Airport a = getById(id); // throws if not found

        if (dto.name != null) a.setName(dto.name);
        if (dto.code != null) a.setCode(dto.code);

        if (dto.cityId != null) {
            City city = cityRepository.findById(dto.cityId)
                    .orElseThrow(() -> new NoSuchElementException("city not found: " + dto.cityId));
            a.setCity(city);
        }

        return airportRepository.save(a);
    }

    // delete airport
    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new NoSuchElementException("airport not found: " + id);
        }
        airportRepository.deleteById(id);
    }
}
