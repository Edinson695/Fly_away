package com.example.fly_away.Service;

import com.example.fly_away.Repository.BookingRepository;
import com.example.fly_away.Repository.FlightRepository;
import com.example.fly_away.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CleanupService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public CleanupService(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void cleanAll() {
         bookingRepository.deleteAll();

         flightRepository.deleteAll();
        userRepository.deleteAll();
    }
}
