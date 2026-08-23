package com.dharm.employee_management_system.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mySuperSecretKeyForJwtThatShouldBeLongAndRandom123456";

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours, in milliseconds

    // Creates a token for a given email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // "subject" = who this token belongs to
                .setIssuedAt(new Date()) // when it was created
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // when it expires
                .signWith(SignatureAlgorithm.HS256, SECRET) // sign it with our secret key
                .compact(); // build the final token string
    }

    // Reads the email back out of a token
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET).parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Checks if a token has expired
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}