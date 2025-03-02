package com.kridan.storage.auth.adapter.in.http;

import com.kridan.storage.auth.application.domain.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    @GetMapping("/validate")
    @Operation(summary = "Валидация токена",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request processed successfully")
            })
    public ResponseEntity<String> validateToken(
            @Parameter(description = "Bearer token", required = true, in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "Bearer token"))
            @RequestHeader("Authorization")
            String authorizationHeader
    ) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Извлекаем токен

            if (jwtService.validateJwt(token)) {
                return ResponseEntity.ok("Token is valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or incorrect");
        }
    }
}
