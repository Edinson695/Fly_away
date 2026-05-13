package com.example.fly_away.Controller;

import com.example.fly_away.DTO.BookingFlightRequest;
import com.example.fly_away.Entity.Booking;
import com.example.fly_away.Service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Long> bookingFlight(@RequestBody BookingFlightRequest flightid) {
        Long bookingId = bookingService.bookFlight(flightid);

        return  ResponseEntity.ok(bookingId);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long request){
        Booking booking = bookingService.findById(request);
        return ResponseEntity.ok(booking);
    }
}
