package com.kridan.storage.auth.application.port.in;

import com.kridan.storage.auth.application.domain.model.User;

public interface GenerateJwtUseCase {
    String generateJwt(User user);
}
