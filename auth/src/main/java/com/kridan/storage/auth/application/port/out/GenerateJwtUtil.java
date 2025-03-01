package com.kridan.storage.auth.application.port.out;

import com.kridan.storage.auth.application.domain.model.User;

public interface GenerateJwtUtil {
    String generateJwt(User user);
}
