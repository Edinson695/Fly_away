package com.example.fly_away.Repository;

import com.example.fly_away.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);
    public Flight findByFlightNumber(String flightNumber);

    @Query("SELECT f FROM Flight f WHERE " +
            "(:flightNumber IS NULL OR f.flightNumber = :flightNumber) AND " +
            "(:airline IS NULL OR f.airline = :airline) AND " +
            "(CAST(:arrivalTime AS java.time.LocalDateTime) IS NULL OR f.arrivalTime = :arrivalTime)"
    )
    List<Flight> searchFlights(@Param("flightNumber") String flightNumber,
                             @Param("airline") String airline,
                             @Param("arrivalTime") LocalDateTime arrivalTime);
}

