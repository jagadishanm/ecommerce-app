package com.example.eCommerceApp.e_Commerce.Application.controller;

import com.example.eCommerceApp.e_Commerce.Application.dto.request.LoginRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.request.RegisterRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.response.AuthResponse;
import com.example.eCommerceApp.e_Commerce.Application.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    /*
        Login API
    */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}