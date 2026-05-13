package com.example.fly_away.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {

    @NotBlank(message = "Ingrese un nombre")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Formato inválido")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]+$", message = "Formato inválido")
    private String lastName;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
    message = "La contraseña debe tener 8 caracteres y al menos 1 y 1 número")
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
