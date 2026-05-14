package com.example.fly_away.Service;

import com.example.fly_away.Entity.Booking;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

    public void sendBookingConfirmation(Booking booking) {
        try {
            String fileName = "flight_booking_email_" + booking.getId() + ".txt";
            Path filePath = Paths.get(fileName);

            DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String salida = booking.getFlight().getDepartureTime().format(iso);
            String llegada = booking.getFlight().getArrivalTime().format(iso);

            // Contenido del correo simulado
            String content = "=== CONFIRMACIÓN DE RESERVA ===\n" +
                    "Nombres: " + booking.getCustomerNames() + "\n" +
                    "Número de Vuelo: " + booking.getFlight().getFlightNumber() + "\n" +
                    "Fecha de Salida: " + salida + "\n" +
                    "Fecha de Llegada: " + llegada + "\n";

            // Guardar el archivo
            Files.writeString(filePath, content);

            System.out.println("Archivo generado: " + fileName);
        } catch (Exception e) {
            System.out.println("Error escribiendo el archivo: " + e.getMessage());
        }
    }
}
