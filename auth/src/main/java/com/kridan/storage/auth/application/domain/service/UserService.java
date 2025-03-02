package com.kridan.storage.auth.application.domain.service;

import com.kridan.storage.auth.application.port.in.SigninUserUseCase;
import com.kridan.storage.auth.application.port.in.SignupUserUseCase;
import com.kridan.storage.auth.application.port.out.ExternalUserStorage;
import com.kridan.storage.auth.application.port.out.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements SignupUserUseCase, SigninUserUseCase {
    private final ExternalUserStorage externalUserStorage;
    private final JwtService jwtService;
    @Override
    public boolean create(String login, String password) {
        return externalUserStorage.createUser(
                login,
                password);
    }

    @Override
    public String login(String login, String password) {
        return jwtService.generateJwt(externalUserStorage.loginUser(login, password));
    }

}
