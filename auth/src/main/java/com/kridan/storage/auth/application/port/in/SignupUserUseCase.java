package com.kridan.storage.auth.application.port.in;

import com.kridan.storage.auth.application.domain.model.User;

public interface SignupUserUseCase {
    boolean create(String login, String password);
}
