package com.example.eCommerceApp.e_Commerce.Application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "yourVerySecretKeyYourVerySecretKey123456";

    /*
        Extract username(email) from token
    */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    /*
        Generic method to extract claims
    */
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /*
        Generate JWT token
    */
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24)
                )
                .signWith(getSignInKey(),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    /*
        Validate token
    */
    public boolean isTokenValid(String token, String email) {

        final String username = extractUsername(token);

        return username.equals(email)
                && !isTokenExpired(token);
    }

    /*
        Check expiry
    */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /*
        Extract expiration date
    */
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    /*
        Extract all claims
    */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
        Secret key conversion
    */
    private Key getSignInKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }
}