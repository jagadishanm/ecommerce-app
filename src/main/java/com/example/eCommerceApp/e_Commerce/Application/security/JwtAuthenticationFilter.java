package com.example.eCommerceApp.e_Commerce.Application.security;

import com.example.eCommerceApp.e_Commerce.Application.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        /*
            Read Authorization header
        */
        final String authHeader =
                request.getHeader("Authorization");

        final String jwt;

        final String userEmail;

        /*
            No token present
        */
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        /*
            Extract token
        */
        jwt = authHeader.substring(7);

        /*
            Extract email from token
        */
        userEmail = jwtService.extractUsername(jwt);

        /*
            Authenticate only if not already authenticated
        */
        if (userEmail != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    (UserDetails) userRepository.findByEmail(userEmail)
                            .orElseThrow();

            /*
                Validate token
            */
            if (jwtService.isTokenValid(
                    jwt,
                    userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                /*
                    Set authenticated user
                */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        /*
            Continue request
        */
        filterChain.doFilter(request, response);
    }
}