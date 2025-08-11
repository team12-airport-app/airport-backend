package com.team12.flightmanagement.dto.airline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAirlineRequest(
        @NotBlank @Size(max = 8) String code,
        @NotBlank @Size(max = 100) String name
) {}