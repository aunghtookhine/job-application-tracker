package com.buildmyself.job_application_tracker.config.security;

import com.buildmyself.job_application_tracker.dto.user.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secretKey,
                      @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expirationMinutes = expirationMinutes;
    }

    public LoginResponse generate(Long id, String email) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .issuer("job-application-tracker")
                .subject(id.toString())
                .claim("email", email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();

        return new LoginResponse(token, "Bearer", expirationMinutes * 60);
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
