
package com.klu.microservices.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.microservices.dto.TokenRequest;
import com.klu.microservices.dto.TokenResponse;
import com.klu.microservices.dto.GenerateTokenRequest;
import com.klu.microservices.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Validate JWT token
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(
            @Valid @RequestBody TokenRequest request) {

        try {
            TokenResponse response =
                    jwtService.validateToken(request.getToken());

            return ResponseEntity.ok(response);

        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid Token"));
        }
    }

    // Generate a token for testing
    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(
            @Valid @RequestBody GenerateTokenRequest request) {

        String token = jwtService.generateToken(
                request.getUsername(),
                request.getRoles()
);
        return ResponseEntity.ok(Map.of("token", token));
    }
}