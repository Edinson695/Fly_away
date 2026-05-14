package com.example.fly_away.Controller;

import com.example.fly_away.Repository.BookingRepository;
import com.example.fly_away.Repository.FlightRepository;
import com.example.fly_away.Repository.UserRepository;
import com.example.fly_away.Service.CleanupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
public class CleanupController {

    private final BookingRepository bookingRepository;
    private final FlightRepository  flightRepository;
    private final UserRepository    userRepository;

    public CleanupController(BookingRepository bookingRepository,
                             FlightRepository flightRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository  = flightRepository;
        this.userRepository    = userRepository;
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cleanup() {
        bookingRepository.deleteAllInBatch();
        flightRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        return ResponseEntity.ok().build();
    }
}
