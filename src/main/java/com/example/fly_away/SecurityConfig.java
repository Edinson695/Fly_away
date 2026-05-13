package com.example.fly_away;

import com.example.fly_away.Repository.UserRepository;
import com.example.fly_away.Security.JwtAuthorizationFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    // Asumo que tienes un AuthenticationProvider configurado, si no lo tienes,
    // coméntame y lo armamos rápido. Es vital para el AuthController.
    //private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;


    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, /*AuthenticationProvider authenticationProvider,*/ UserRepository userRepository) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        //this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService());

        authProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return authProvider;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Desactivamos CSRF (necesario para APIs REST con Tokens)
                .csrf(csrf -> csrf.disable())

                // 2. Configuramos qué rutas son públicas y cuáles protegidas
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // AQUÍ ESTÁ LA MAGIA: Liberamos el registro y el login
                                .requestMatchers("/users/register", "/auth/login", "/error").permitAll()
                                // Cualquier otra petición (como /flights/book) requerirá Token
                                .anyRequest().authenticated()
                )

                // 3. Le decimos que no guarde sesiones en memoria (porque usamos Tokens)
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Agregamos el proveedor y tu filtro JWT
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}
