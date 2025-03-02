package com.kridan.storage.auth.adapter.in.http;

import com.kridan.storage.auth.application.domain.exceptions.UserAlreadyExistsException;
import com.kridan.storage.auth.application.port.in.SigninUserUseCase;
import com.kridan.storage.auth.application.port.in.SignupUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User API", description = "Методы для работы с пользователями")
public class UserController {
    private final SignupUserUseCase signupUserUseCase;
    private final SigninUserUseCase signinUserUseCase;

    @PostMapping("/signup")
    @Operation(
            summary = "Создать пользователя",
            description = "Добавляет нового пользователя в систему",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь создан",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "409", description = "Пользователь с таким логином уже существует")
            }
    )
    public ResponseEntity<?> signUp(
            @RequestBody CredentialsDto credentialsDto
    ) {
        try {
            if(signupUserUseCase.create(credentialsDto.getLogin(), credentialsDto.getPassword())){

                return ResponseEntity.ok("User created successfully");

            } else {

                throw new UserAlreadyExistsException(
                        String.format("User with login %s already exists", credentialsDto.getLogin())
                );
            }

        } catch (UserAlreadyExistsException uae){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( String.format("User with login %s already exists", credentialsDto.getLogin()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("INTERNAL_SERVER_ERROR");
        }
    }

    @PostMapping("/signin")
    @Operation(
            summary = "Авторизировать пользователя",
            description = "Авторизирует пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь авторизирован",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Пользователь не найден")
            }
    )
    public ResponseEntity<?> signIn(
            @RequestBody CredentialsDto credentialsDto
    ) {
        try {
            return new ResponseEntity<>(
                    new JwtResponse(signinUserUseCase.login(credentialsDto.getLogin(), credentialsDto.getPassword())),
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
