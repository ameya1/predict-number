package org.predict.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.entity.SystemHealth;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@Log4j2
public class HealthController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SystemHealth> create() {
        SystemHealth systemHealth = SystemHealth.builder().message("Healthy").build();
        try {
            return ResponseEntity.ok(systemHealth);
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            systemHealth.setMessage("Unhealthy");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(systemHealth);
        }
    }
}
