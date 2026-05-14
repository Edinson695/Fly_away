package com.example.fly_away.Service;

import com.example.fly_away.DTO.BookingFlightRequest;
import com.example.fly_away.Entity.Booking;
import com.example.fly_away.Entity.Flight;
import com.example.fly_away.Entity.User;
import com.example.fly_away.Repository.BookingRepository;
import com.example.fly_away.Repository.FlightRepository;
import com.example.fly_away.Repository.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    private final NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          FlightRepository flightRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.notificationService = notificationService;
    }

    public Long bookFlight(BookingFlightRequest flightRequest){
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
            notificationService.sendBookingConfirmation(booking);
        } catch (Exception e) {
            System.out.println("Error al generar el archivo txt: " + e.getMessage());
        }

        return booking.getId();
    }

    public Booking findById(Long id){
        return bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Reserva no encontrada con el ID: " + id));
    }

}
