package com.coffeeshop.backend.controller;


import com.coffeeshop.backend.dto.AuthResponse;
import com.coffeeshop.backend.dto.ErrorResponse;
import com.coffeeshop.backend.dto.LoginRequest;
import com.coffeeshop.backend.dto.RegisterRequest;
import com.coffeeshop.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        try {
            String message = authService.register(request);
            return ResponseEntity.status(201).body(Map.of("message", message));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(error);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }



}
