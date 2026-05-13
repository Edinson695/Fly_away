package com.example.fly_away.Service;

import com.example.fly_away.DTO.FlightCreateRequest;
import com.example.fly_away.Entity.Flight;
import com.example.fly_away.Mapper.FlightMapper;
import com.example.fly_away.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    public Long createFlight(FlightCreateRequest request) {
        if (flightRepository.existsByFlightNumber(
                request.getFlightNumber())) {

            throw new IllegalArgumentException(
                    "El número de vuelo ya existe"
            );
        }

        if (!request.getDepartureTime()
                .isBefore(request.getArrivalTime())) {

            throw new IllegalArgumentException(
                    "La salida debe ser antes que la llegada"
            );
        }

        Flight flight = FlightMapper.toEntity(request);

        flight = flightRepository.save(flight);

        return flight.getId();
    }

    public List<Flight> search(String flightNumber, String airline, LocalDateTime arrivalTime) {
        return flightRepository.searchFlights(flightNumber, airline, arrivalTime);
    }
}
