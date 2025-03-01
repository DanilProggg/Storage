package com.kridan.storage.auth.application.port.in;

public interface AuthUserUseCase {
    boolean auth(String jwt);
}
