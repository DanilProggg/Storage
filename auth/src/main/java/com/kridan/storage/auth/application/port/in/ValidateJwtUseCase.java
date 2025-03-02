package com.kridan.storage.auth.application.port.in;

public interface ValidateJwtUseCase {
    boolean validateJwt(String jwt);
}
