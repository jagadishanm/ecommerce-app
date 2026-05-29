package com.example.eCommerceApp.e_Commerce.Application.service;

import com.example.eCommerceApp.e_Commerce.Application.dto.request.LoginRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.request.RegisterRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.response.AuthResponse;
import com.example.eCommerceApp.e_Commerce.Application.entity.User;
import com.example.eCommerceApp.e_Commerce.Application.entity.enums.Role;
import com.example.eCommerceApp.e_Commerce.Application.repository.UserRepository;
import com.example.eCommerceApp.e_Commerce.Application.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());


        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(Role.USER);

        userRepository.save(user);


        String jwtToken =
                jwtService.generateToken(user.getEmail());

        return new AuthResponse(jwtToken);
    }


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken =
                jwtService.generateToken(user.getEmail());

        return new AuthResponse(jwtToken);
    }
}