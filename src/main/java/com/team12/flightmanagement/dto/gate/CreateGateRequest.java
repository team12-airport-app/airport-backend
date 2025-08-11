package com.team12.flightmanagement.dto.gate;

public class CreateGateRequest {
    public String code;
    public String description;
    public String airportCode; // preferred
    public Long airportId;     // alternative
}