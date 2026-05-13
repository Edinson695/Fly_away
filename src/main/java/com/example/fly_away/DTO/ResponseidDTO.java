package com.example.fly_away.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseidDTO {
    @Column(nullable = false)
    private Long id;
}
