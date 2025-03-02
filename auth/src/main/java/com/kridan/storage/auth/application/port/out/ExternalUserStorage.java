package com.kridan.storage.auth.application.port.out;

import com.kridan.storage.auth.application.domain.model.User;

public interface ExternalUserStorage {
    boolean createUser(String login, String password);
    User loginUser(String login, String password);

}
