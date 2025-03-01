package com.kridan.storage.auth.application.port.in;

public interface SigninUserUseCase {
    String login(String login, String password);
}
