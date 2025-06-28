package com.team12.flightmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team12.flightmanagement.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {}
