package com.kridan.storage.auth.application.port.out;

import com.kridan.storage.auth.application.domain.model.User;

public interface ExternalStorage {
    boolean createUser(String login, String password);
    User loginUser(String login, String password);
}
