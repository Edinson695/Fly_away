package com.example.fly_away.Controller;

import com.example.fly_away.DTO.FlightCreateRequest;
import com.example.fly_away.Entity.Flight;
import com.example.fly_away.Repository.FlightRepository;
import com.example.fly_away.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    private final FlightRepository flightRepository;
    public FlightController(FlightService flightService, FlightRepository flightRepository) {
        this.flightService = flightService;
        this.flightRepository = flightRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createFlight(
            @Valid @RequestBody FlightCreateRequest request){

        Long flightid = flightService.createFlight(request);

        return ResponseEntity.ok(flightid);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlight(
            @RequestParam(required = false) String flightnumber,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false)LocalDateTime arrivalTime
            ){
        List<Flight> result = flightService.search(flightnumber, airline, arrivalTime);
        return ResponseEntity.ok(result);
    }

}
