package com.team12.flightmanagement.dto.gate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateGateRequest(
        @NotBlank @Size(max = 8) String code,
        @Size(max = 120) String description,
        String airportCode,
        Long airportId
) {}