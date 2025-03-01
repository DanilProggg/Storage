package com.kridan.storage.auth.application.domain.service;

import com.kridan.storage.auth.application.domain.model.User;
import com.kridan.storage.auth.application.port.out.GenerateJwtUtil;
import com.kridan.storage.auth.application.port.out.ValidateJwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService implements GenerateJwtUtil, ValidateJwtUtil {
    @Value("${jwt.key}")
    private String secretKeyString;
    @Value("${jwt.expiration}")
    private long expirationTime;
    @Override
    public String generateJwt(User user) {
        SecretKey secretKey = createSecretKeyFromString(secretKeyString);
        Date exexpirationDate = new Date(new Date().getTime() + expirationTime);
        return Jwts.builder()
                .signWith(secretKey)
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(exexpirationDate)
                .claim("username", user.getLogin())
                .compact();

    }

    @Override
    public boolean validateJwt(String jwt) {
        SecretKey secretKey = createSecretKeyFromString(secretKeyString);
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
            return true;
        } catch (Exception e) {
            return false; // Токен невалиден
        }
    }

    // Функция для создания SecretKey из строки
    public SecretKey createSecretKeyFromString(String secretKeyString) {
        byte[] secretKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(secretKeyBytes, "HmacSHA256");
    }
}
