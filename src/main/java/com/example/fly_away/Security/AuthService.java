package com.example.fly_away.Security;

import com.example.fly_away.DTO.SignUpRequest;
import com.example.fly_away.DTO.TokenResponse;
import com.example.fly_away.Entity.User;
import com.example.fly_away.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username).orElseThrow();
    }

    public TokenResponse signUp(SignUpRequest request){
        User user = userRepository.save(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword())
                        )
        );
        var token = jwtService.generateToken(user);
        return new TokenResponse(token);
    }

    public TokenResponse signIn(String username, String password){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        User account = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(account);

        return new TokenResponse(token);
    }
}
