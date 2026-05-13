package com.example.fly_away;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FlyAwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyAwayApplication.class, args);
    }

}
