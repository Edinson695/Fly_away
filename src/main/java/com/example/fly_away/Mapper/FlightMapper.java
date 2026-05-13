package com.example.fly_away.Mapper;

import com.example.fly_away.DTO.FlightCreateRequest;
import com.example.fly_away.Entity.Flight;

public class FlightMapper {
    public static Flight toEntity(FlightCreateRequest request) {

        Flight flight = new Flight();

        flight.setFlightNumber(request.getFlightNumber());
        flight.setAirline(request.getAirline());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setAvailableSeats(request.getAvailableSeats());

        return flight;
    }
}
