package com.kridan.storage.auth.adapter.in.http;

import com.kridan.storage.auth.application.domain.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Извлекаем токен

            if (authService.validateJwt(token)) {
                return ResponseEntity.ok("Token is valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or incorrect");
        }
    }
}
