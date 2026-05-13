package com.example.fly_away.Service;

import com.example.fly_away.DTO.BookingFlightRequest;
import com.example.fly_away.Entity.Booking;
import com.example.fly_away.Entity.Flight;
import com.example.fly_away.Entity.User;
import com.example.fly_away.Repository.BookingRepository;
import com.example.fly_away.Repository.FlightRepository;
import com.example.fly_away.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          FlightRepository flightRepository,  EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.emailService = emailService;
    }

    public Long bookFlight(BookingFlightRequest flightRequest){
        // Esto saca el correo (email) guardado dentro del Token
        String emailDelUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuario = userRepository.findByEmail(emailDelUsuario).orElseThrow();

        Flight flight = flightRepository.findById(flightRequest.getFlightId()).orElseThrow(()
                -> new RuntimeException("Vuelo no encontrado"));

        if (flight.getAvailableSeats() <= 0){
            throw new RuntimeException("El vuelo ya no tiene asientos disponibles");
        }

        Booking booking = new Booking();
        booking.setFlight(flight);

        booking.setUser(usuario);
        booking.setCustomerNames(usuario.getName() + " " +  usuario.getLastName());
        booking.setBookingDate(LocalDateTime.now());

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        booking = bookingRepository.save(booking);
        try {
            emailService.sendBookingConfirmation(
                    usuario.getEmail(),
                    usuario.getName(),
                    flight.getFlightNumber()
            );
        } catch (Exception e) {
            System.out.println("Error al enviar el email" + e.getMessage());
        }

        return booking.getId();
    }

    public Booking findById(Long id){
        return bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Reserva no encontrada con el ID: " + id));
    }

}
