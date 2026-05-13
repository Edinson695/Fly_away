package com.example.fly_away.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightCreateRequest {

    @NotBlank(message = "El número de vuelo es requerido")
    @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "Formato inválido")
    private String flightNumber;

    @NotBlank(message = "La aerolínea es requerida")  // ← agregar
    private String airline;

    @NotNull(message = "La fecha es requerida")
    private LocalDateTime departureTime;

    @NotNull(message = "La fecha es requerida")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Los asientos son requeridos")
    @Min(value = 1, message = "Almenos 1 asiento disponible")
    private Integer availableSeats;
}
