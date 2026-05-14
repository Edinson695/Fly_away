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
        // ¡EL ORDEN ES VITAL!
        // 1. Primero borramos la tabla hija (Booking)
        bookingRepository.deleteAll();

        // 2. Luego ya podemos borrar las tablas padre (Flights y Users)
        flightRepository.deleteAll();
        userRepository.deleteAll();
    }
}
