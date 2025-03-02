package com.kridan.storage.auth.application.domain.service;

import com.kridan.storage.auth.application.port.in.SigninUserUseCase;
import com.kridan.storage.auth.application.port.in.SignupUserUseCase;
import com.kridan.storage.auth.application.port.out.ExternalStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements SignupUserUseCase, SigninUserUseCase {
    private final ExternalStorage externalStorage;
    private final JwtService jwtService;
    @Override
    public boolean create(String login, String password) {
        return externalStorage.createUser(
                login,
                password);
    }

    @Override
    public String login(String login, String password) {
        return jwtService.generateJwt(externalStorage.loginUser(login, password));
    }

}
