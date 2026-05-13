package com.example.fly_away.Controller;

import com.example.fly_away.DTO.TokenResponse;
import com.example.fly_away.Security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginResquet request) {
        if (request == null) {
            throw new RuntimeException("Request null");
        }

        TokenResponse token = authService.signIn(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
