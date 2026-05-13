package com.example.fly_away.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendBookingConfirmation(String toEmail, String customerName, String flightNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmación de Reserva - Fly Away");
        message.setText("Hola " + customerName + ",\n\n" +
                "¡Tu reserva ha sido exitosa!\n" +
                "Número de Vuelo: " + flightNumber + "\n\n" +
                "Gracias por confiar en Fly Away."+ "\n"
        );

        mailSender.send(message);
    }
}
