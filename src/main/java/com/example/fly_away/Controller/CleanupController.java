package com.example.fly_away.Controller;

import com.example.fly_away.Service.CleanupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CleanupController {

    private final CleanupService cleanupService;

    public CleanupController(CleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanupDatabase() {
        cleanupService.cleanAll();
        return ResponseEntity.ok("Base de datos completamente limpia. ¡Que pasen los tests!");
    }
}
