package com.kridan.storage.auth.adapter.in.http;

import com.kridan.storage.auth.application.port.in.AuthUserUseCase;
import com.kridan.storage.auth.application.port.in.SignupUserUseCase;
import com.kridan.storage.auth.application.port.in.SigninUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {
    private final SignupUserUseCase signupUserUseCase;
    private final SigninUserUseCase signinUserUseCase;
    private final AuthUserUseCase authUserUseCase;

    @GetMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody CredentialsDto credentialsDto
    ) {
        try {
            return new ResponseEntity<>(
                    signupUserUseCase.create(credentialsDto.getLogin(), credentialsDto.getPassword()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/signin")
    public ResponseEntity<?> signIn(
            @RequestBody CredentialsDto credentialsDto
    ) {
        try {
            return new ResponseEntity<>(
                    signinUserUseCase.login(credentialsDto.getLogin(), credentialsDto.getPassword()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
