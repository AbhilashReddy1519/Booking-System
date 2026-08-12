package com.app.bs.booking_system.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.users.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
  private final SecretKey secretKey;

  JWTService(@Value("${jwt.secret_key}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UUID userId, Role role) {
    return Jwts.builder()
        .subject(userId.toString())
        .claim("role", role.name())
        .issuedAt(new Date())
        .expiration(
          new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24) 
        )
        .signWith(secretKey)
        .compact();
  }
}
